package com.mbankingloan.mbankingloan.Feature.Customer.Service.dto;


import com.mbankingloan.mbankingloan.Domain.Customer;
import com.mbankingloan.mbankingloan.Domain.LoanAccount;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.CustomerRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.LoanAccountRepository;
import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Request.CheckLoanAccount;
import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Response.ResponseLoanAccountDetails;
import com.mbankingloan.mbankingloan.Mapper.LoanAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanAccountCustomerServiceImpl implements LoanAccountCustomerService {

    private final LoanAccountRepository loanAccountRepository;
    private final CustomerRepository customerRepository;
    private final LoanAccountMapper loanAccountMapper;


    @Override
    public List<ResponseLoanAccountDetails> checkLoanAccount(CheckLoanAccount checkLoanAccount,String username) {

        // Validate if the phone number exists
        if (!loanAccountRepository.existsByPhoneNumber(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone Number not found");
        }

        // Retrieve loan accounts by phone number
        List<LoanAccount> loanAccounts = loanAccountRepository.findAllByPhoneNumber(username);

        List<Customer> customers = customerRepository.findAllByphoneNumber(username);

        // Check if any account pin matches the provided pin
        boolean pinIncorrect = customers.stream()
                .anyMatch(e -> !e.getPin().equals(checkLoanAccount.pin()));

        if (pinIncorrect) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pin is incorrect");
        }

        // Map and return the loan account details
        return loanAccountMapper.toResponseLoanAccountDetailsList(loanAccounts);
    }

    }



