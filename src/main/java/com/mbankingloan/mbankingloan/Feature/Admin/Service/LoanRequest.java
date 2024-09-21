package com.mbankingloan.mbankingloan.Feature.Admin.Service;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.*;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoanType;
import jakarta.validation.Valid;

import java.util.List;

public interface LoanRequest {

       ResponseLoanType createLoanType(CreateLoanType createLoanType);

       ResponseLoan createLoan(CreateLoan createLoan);

       List<ResponseLoan> getAllLoans();

       List<ResponseLoanType> getAllLoanTypes();

       ResponseLoan deleteLoanByid(DeleteLoan deleteLoan);

       ResponseLoanType deleteLoanTypeByid(DeleteLoan deleteLoan);

       ResponseLoan recoverLoanByid(@Valid RecoverLoan recoverLoan);

       ResponseLoanType recoverLoanTypeByid(@Valid RecoverLoanType recoverLoanType);
}
