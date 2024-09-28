package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto;


import com.mbankingloan.mbankingloan.Domain.Customer;
import com.mbankingloan.mbankingloan.Domain.LoanAccount;
import com.mbankingloan.mbankingloan.Domain.LoanApplication;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanApplicationRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.UserRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.CustomerRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.LoanAccountRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.CreateLoanAccount;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.DrawDownLoanAccount;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseLoanAccount;
import com.mbankingloan.mbankingloan.Mapper.LoanAccountMapper;
import com.mbankingloan.mbankingloan.Util.GenerateLoanAccountNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final LoanAccountMapper loanAccountMapper;
    private final GenerateLoanAccountNumber generateLoanAccountNumber;


    @Override
    @Transactional
    public ResponseLoanAccount createLoanAccount(CreateLoanAccount createLoanAccount, String staffId) {

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


        LoanAccount loanAccount = loanAccountMapper.fromCreateLoanAccountRequest(createLoanAccount);
        loanAccount.setCustomer(customerRepository.findAllByCustomerCIFNumberIn(createLoanAccount.CustomerCif()));
        loanAccount.setActNo(generateLoanAccountNumber.generateLoanAccountNumber());
        loanAccount.setLoanApplication(loanApplicationRepository.findById(createLoanAccount.loanApplicationId()).orElseThrow());
        loanAccount.setBalance(BigDecimal.ZERO);
        loanAccount.setCreatedAt(LocalDate.now());
        loanAccount.setIsActive(false);
        loanAccount.setIsDeleted(false);
        loanAccount.setUser(userRepository.findByStaffId(staffId));
        loanAccountRepository.save(loanAccount);

        return loanAccountMapper.toResponseLoanAccountRequest(loanAccount);
    }

    @Override
    public ResponseLoanAccount deleteLoanAccount(Integer id, String staffId) {
        LoanAccount loanAccount = loanAccountRepository.findById(id).orElseThrow();
        if (loanAccount.getIsActive()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "LoanAccount is Active Cannot Be Closed");
        }
        loanAccount.setIsActive(false);
        loanAccount.setIsDeleted(true);
        loanAccount.setUser(userRepository.findByStaffId(staffId));
        return loanAccountMapper.toResponseLoanAccountRequest(loanAccount);

    }


    @Override
    @Transactional
    public ResponseLoanAccount drawDownLoan(DrawDownLoanAccount drawDownLoanAccount, String staffId) {
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
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan Application Does not exists");
        }

        if (loanApplication.getIsDrawDown()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan Application is already Draw Down");
        }
        if (!loanAccount.getIsDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please Customer set new Pin and Password");

        }
        loanAccount.setBalance(loanApplication.getRequestAmount());
        loanAccount.setIsActive(true);
        loanApplication.setIsDrawDown(true);
        loanAccount.setUser(userRepository.findByStaffId(staffId));
        return loanAccountMapper.toResponseLoanAccountRequest(loanAccount);
    }
}
