package com.mbankingloan.mbankingloan.Feature.Customer.Service.dto;


import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Request.CheckLoanAccount;
import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Response.ResponseLoanAccountDetails;

import java.util.List;

public interface LoanAccountCustomerService {


    List<ResponseLoanAccountDetails> checkLoanAccount(CheckLoanAccount checkLoanAccount, String username);
}
