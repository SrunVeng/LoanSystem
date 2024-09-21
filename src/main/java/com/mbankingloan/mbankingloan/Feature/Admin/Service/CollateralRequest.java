package com.mbankingloan.mbankingloan.Feature.Admin.Service;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateCollateralType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.DeleteCollateralType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RecoverCollateralType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseCollateralType;
import jakarta.validation.Valid;

import java.util.List;

public interface CollateralRequest {

       ResponseCollateralType createCollateralType(CreateCollateralType createCollateralType);

       List<ResponseCollateralType> getAllCollateralTypes();

       ResponseCollateralType deleteCollateralType(@Valid DeleteCollateralType deleteCollateral);

       ResponseCollateralType recoverCollateralType(@Valid RecoverCollateralType recoverCollateral);

    ResponseCollateralType getCollateralTypeById(int id);
}
