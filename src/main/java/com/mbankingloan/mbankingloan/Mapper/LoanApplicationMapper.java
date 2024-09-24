package com.mbankingloan.mbankingloan.Mapper;


import com.mbankingloan.mbankingloan.Domain.LoanApplication;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Request.CreateLoanApplication;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Response.ResponseLoanApplication;
import com.mbankingloan.mbankingloan.Feature.Management.Service.dto.Response.ResponseLoanApplicationDetails;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface LoanApplicationMapper {

    LoanApplication fromCreateLoanApplicationRequest(CreateLoanApplication createLoanApplication);
    ResponseLoanApplication toResponseLoanApplication(LoanApplication loanApplication);

    List<ResponseLoanApplicationDetails> toResponseLoanApplicationDetailsList(List<LoanApplication> loanApplications);

    ResponseLoanApplicationDetails toResponseLoanApplicationDetails(LoanApplication loanApplications);


}
