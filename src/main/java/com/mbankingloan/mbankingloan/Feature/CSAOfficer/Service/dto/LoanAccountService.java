package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto;


import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.CreateLoanAccount;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.DrawDownLoanAccount;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseLoanAccount;
import jakarta.validation.Valid;

public interface LoanAccountService {


    ResponseLoanAccount createLoanAccount(@Valid CreateLoanAccount createLoanAccount, String staffId);

    ResponseLoanAccount deleteLoanAccount(Integer id,String staffId);

    ResponseLoanAccount drawDownLoan(@Valid DrawDownLoanAccount drawDownLoanAccount,String staffId);
}
