package com.mbankingloan.mbankingloan.Feature.Admin.Service;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateLoanType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoanType;

import java.util.List;

public interface LoanRequest {

       ResponseLoanType createLoanType(CreateLoanType createLoanType);

       ResponseLoan createLoan(CreateLoan createLoan);

       List<ResponseLoan> getAllLoans();

       List<ResponseLoanType> getAllLoanTypes();
}
