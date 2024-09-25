package com.mbankingloan.mbankingloan.Feature.Customer.Service.dto;


import com.mbankingloan.mbankingloan.Domain.LoanAccount;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.LoanAccountRepository;
import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Request.CheckLoanAccount;
import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Response.ResponseLoanAccountDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanAccountCustomerServiceImpl implements LoanAccountCustomerService {

    private final LoanAccountRepository loanAccountRepository;


    @Override
    public ResponseLoanAccountDetails checkLoanAccount(CheckLoanAccount checkLoanAccount) {

        LoanAccount loanAccout = loanAccountRepository.findByActNo(checkLoanAccount.actNo());


    return null;

    }
}
