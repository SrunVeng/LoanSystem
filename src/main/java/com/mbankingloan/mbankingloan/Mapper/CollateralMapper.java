package com.mbankingloan.mbankingloan.Mapper;


import com.mbankingloan.mbankingloan.Domain.CollateralType;
import com.mbankingloan.mbankingloan.Domain.Loan;
import com.mbankingloan.mbankingloan.Domain.LoanType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateCollateralType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateLoanType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseCollateralType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoanType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CollateralMapper {

    CollateralType fromCollateralTypeRequest(CreateCollateralType createCollateralType);

    ResponseCollateralType toCollateralTypeResponse(CollateralType collateralType);

    List<ResponseCollateralType> toAllCollateralTypesResponse(List<CollateralType> collateralTypes);


}
