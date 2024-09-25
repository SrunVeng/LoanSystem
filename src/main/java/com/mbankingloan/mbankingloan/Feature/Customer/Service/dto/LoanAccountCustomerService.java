package com.mbankingloan.mbankingloan.Feature.Customer.Service.dto;


import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Request.CheckLoanAccount;
import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Response.ResponseLoanAccountDetails;
import jakarta.validation.Valid;

public interface LoanAccountCustomerService {
    ResponseLoanAccountDetails checkLoanAccount(@Valid CheckLoanAccount checkLoanAccount);
}
