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

        if(!loanApplicationRepository.existsById(createLoanAccount.loanApplicationId())){
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
        loanAccount.setCreatedAt(LocalDate.now());
        loanAccount.setLoanAccountType(loanAccountType);
        loanAccount.setIsActive(false);
        loanAccount.setIsDeleted(false);
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
        return null;
    }
}
