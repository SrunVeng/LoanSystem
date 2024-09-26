package com.mbankingloan.mbankingloan.Feature.Customer.Service.dto;


import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Request.CheckLoanAccount;
import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Response.ResponseLoanAccountDetails;
import jakarta.validation.Valid;

import java.util.List;

public interface LoanAccountCustomerService {
    List<ResponseLoanAccountDetails> checkLoanAccount(@Valid CheckLoanAccount checkLoanAccount, String phoneNumber);
}
