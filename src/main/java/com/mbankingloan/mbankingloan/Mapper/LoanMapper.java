package com.mbankingloan.mbankingloan.Mapper;


import com.mbankingloan.mbankingloan.Domain.Loan;
import com.mbankingloan.mbankingloan.Domain.LoanType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateLoanType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoanAccountType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoanType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    LoanType fromLoanTypeRequest(CreateLoanType createLoanType);

    ResponseLoanType toResponseLoanType(LoanType loanType);

    Loan fromLoanRequest(CreateLoan createLoan);

    ResponseLoan toLoanResponse(Loan loan);

    List<ResponseLoan> toAllLoanResponse(List<Loan> loans);

    List<ResponseLoanType> toAllLoanTypeResponse(List<LoanType> loanTypes);






}
