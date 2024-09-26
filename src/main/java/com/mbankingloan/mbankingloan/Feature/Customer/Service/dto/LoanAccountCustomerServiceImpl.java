package com.mbankingloan.mbankingloan.Feature.Customer.Service.dto;


import com.mbankingloan.mbankingloan.Domain.LoanAccount;
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
    private final LoanAccountMapper loanAccountMapper;


    @Override
    public List<ResponseLoanAccountDetails> checkLoanAccount(CheckLoanAccount checkLoanAccount, String phoneNumber) {



        if(!loanAccountRepository.existsByPhoneNumber(phoneNumber)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone Number not found");
        }



        List<LoanAccount> loanAccount = loanAccountRepository.findAllByPhoneNumber(phoneNumber);


        return loanAccountMapper.toResponseLoanAccountDetailsList(loanAccount);

    }
}
