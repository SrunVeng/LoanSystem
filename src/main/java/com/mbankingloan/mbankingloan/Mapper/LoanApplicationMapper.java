package com.mbankingloan.mbankingloan.Mapper;


import com.mbankingloan.mbankingloan.Domain.LoanApplication;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Request.CreateLoanApplication;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Response.ResponseLoanApplication;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LoanApplicationMapper {

    LoanApplication fromCreateLoanApplicationRequest(CreateLoanApplication createLoanApplication);
    ResponseLoanApplication toResponseLoanApplication(LoanApplication loanApplication);






}
