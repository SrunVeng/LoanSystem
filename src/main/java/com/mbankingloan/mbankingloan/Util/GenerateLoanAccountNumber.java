package com.mbankingloan.mbankingloan.Util;

import com.mbankingloan.mbankingloan.Domain.LoanAccount;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.CustomerRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.LoanAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class GenerateLoanAccountNumber {

    private final LoanAccountRepository loanAccountRepository;


    public String generateLoanAccountNumber() {
        String loanAccountNumber;
        Random random = new Random();

        do {
            loanAccountNumber = String.format("000%09d", (long)(random.nextInt(1000000000))); // Generate a 6-digit number
        } while (loanAccountRepository.existsByactNo(loanAccountNumber));

        return loanAccountNumber;
    }

}
