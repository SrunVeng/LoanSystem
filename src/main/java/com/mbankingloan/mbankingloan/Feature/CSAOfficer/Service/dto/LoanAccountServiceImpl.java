package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto;


import com.mbankingloan.mbankingloan.Domain.*;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanApplicationRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.CustomerRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.LoanAccountRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.LoanAccountTypeRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.CreateLoanAccount;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.DrawDownLoanAccount;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseLoanAccount;
import com.mbankingloan.mbankingloan.Mapper.LoanAccountMapper;
import com.mbankingloan.mbankingloan.Util.GenerateLoanAccountNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LoanAccountServiceImpl implements LoanAccountService {

    private final LoanAccountRepository loanAccountRepository;
    private final LoanAccountTypeRepository loanAccountTypeRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;
    private final LoanAccountMapper loanAccountMapper;
    private final GenerateLoanAccountNumber generateLoanAccountNumber;
    private final PasswordEncoder passwordEncoder;

    @Value("${home-loan.down-payment}")
    private BigDecimal HomeLoanDownPayment;


    @Override
    @Transactional
    public ResponseLoanAccount createLoanAccount(CreateLoanAccount createLoanAccount) {

        List<String> customerCifs = createLoanAccount.CustomerCif();
        if (customerCifs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CustomerCif list cannot be empty");
        }

        List<Customer> customers = customerRepository.findAllByCustomerCIFNumberIn(customerCifs);
        if (customers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customers do not exist");
        }

        if (!loanApplicationRepository.existsById(createLoanAccount.loanApplicationId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "LoanApplication do not exists");
        }

        LoanApplication loanApplication = loanApplicationRepository.findById(createLoanAccount.loanApplicationId()).orElseThrow();
        LoanType loanType = loanApplication.getLoanType();
        Integer id = loanType.getId();


        LoanAccountType loanAccountType = loanAccountTypeRepository.findById(id).orElseThrow();

        LoanAccount loanAccount = loanAccountMapper.fromCreateLoanAccountRequest(createLoanAccount);
        loanAccount.setCustomer(customerRepository.findAllByCustomerCIFNumberIn(createLoanAccount.CustomerCif()));
        loanAccount.setActNo(generateLoanAccountNumber.generateLoanAccountNumber());
        loanAccount.setLoanApplication(loanApplicationRepository.findById(createLoanAccount.loanApplicationId()).orElseThrow());
        loanAccount.setBalance(BigDecimal.ZERO);
        loanAccount.setCreatedAt(LocalDate.now());
        loanAccount.setLoanAccountType(loanAccountType);
        loanAccount.setIsActive(false);
        loanAccount.setIsDeleted(false);
        loanAccount.setIsNewSecuritySet(false);
        loanAccount.setPassword(passwordEncoder.encode("123456"));
        loanAccount.setPin(passwordEncoder.encode("1234"));
        loanAccountRepository.save(loanAccount);

        return loanAccountMapper.toResponseLoanAccountRequest(loanAccount);
    }

    @Override
    public ResponseLoanAccount deleteLoanAccount(Integer id) {
        LoanAccount loanAccount = loanAccountRepository.findById(id).orElseThrow();
        if (loanAccount.getIsActive()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "LoanAccount is Active Cannot Be Closed");
        }
        loanAccount.setIsActive(false);
        loanAccount.setIsDeleted(true);
        return loanAccountMapper.toResponseLoanAccountRequest(loanAccount);

    }


    @Override
    @Transactional
    public ResponseLoanAccount drawDownLoan(DrawDownLoanAccount drawDownLoanAccount) {
        //Declare
        List<String> cifNumbers = drawDownLoanAccount.CustomerCif();
        List<Customer> existingCustomers = customerRepository.findAllByCustomerCIFNumberIn(cifNumbers);
        List<String> existingCifNumbers = existingCustomers.stream().map(Customer::getCustomerCIFNumber) // Adjust this to your actual method for getting the CIF number
                .toList();
        boolean anyNonExistent = cifNumbers.stream().anyMatch(cif -> !existingCifNumbers.contains(cif));
        // if LoanAccount and LoanApplication is Linked
        LoanAccount loanAccount = loanAccountRepository.findByActNo(drawDownLoanAccount.loanAccountNumber());
        LoanApplication loanApplication = loanApplicationRepository.findById(drawDownLoanAccount.loanApplicationId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan Application not found"));
        if (loanAccount.getLoanApplication() == null || !loanAccount.getLoanApplication().equals(loanApplication)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan Account and Loan Application are not linked");
        }
        // can draw down if all approval is true
        if (!loanApplication.getIsApprovedByHeadOfLoan()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan Application is Not Yet Approve By Head of Loan");
        }
        if (!loanApplication.getIsApprovedByBranchManager()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan Application is Not Yet Approve By Branch Manager");
        }

        if (loanApplication.getIsRejectedByBM()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan Application is Rejected By BM");
        }

        if (loanApplication.getIsRejectedByHeadOfLoan()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan Application is Rejected By Head Of Loan");
        }

        // if List of CustomerCIF is correct
        if (anyNonExistent) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "One or more Customer CIF numbers do not exist.");
        }

        // if LoanApplicationID is correct
        if (!loanAccountRepository.existsByactNo(drawDownLoanAccount.loanAccountNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan Account Does not exist");
        }

        if (!loanApplicationRepository.existsById(drawDownLoanAccount.loanApplicationId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan Application Does notexists");
        }

        if (loanApplication.getIsDrawDown()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan Application is already Draw Down");
        }
        // Can DrawDown if password is reset out of 123456 and pin out of 1234 ( validate later when have customer feature )
        if(!loanAccount.getIsNewSecuritySet()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please Customer set new Pin and Password");

        }

        // home loan draw Only70%
        if (loanApplication.getLoanType().getId().equals(3)) {
            loanAccount.setBalance(loanApplication.getRequestAmount().subtract(loanApplication.getRequestAmount().multiply(HomeLoanDownPayment)));
        } else {
            loanAccount.setBalance(loanApplication.getRequestAmount());
        }
        loanAccount.setIsActive(true);
        loanApplication.setIsDrawDown(true);
        return loanAccountMapper.toResponseLoanAccountRequest(loanAccount);
    }
}
