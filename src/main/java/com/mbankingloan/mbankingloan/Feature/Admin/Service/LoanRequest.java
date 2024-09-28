package com.mbankingloan.mbankingloan.Feature.Admin.Service;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateLoanType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.DeleteLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoanAccountType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoanType;

import java.util.List;

public interface LoanRequest {

    ResponseLoanType createLoanType(CreateLoanType createLoanType);

    ResponseLoan createLoan(CreateLoan createLoan);

    List<ResponseLoan> getAllLoans();

    List<ResponseLoanType> getAllLoanTypes();

    ResponseLoan deleteLoan(DeleteLoan deleteLoan);

    ResponseLoanType deleteLoanType(DeleteLoan deleteLoan);

    ResponseLoan recoverLoanById(int id);

    ResponseLoanType recoverLoanTypeById(int id);

    ResponseLoan getLoanById(int id);

    ResponseLoanType getLoanTypeById(int id);


}
