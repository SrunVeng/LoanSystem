package com.mbankingloan.mbankingloan.Feature.Admin.Service;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateCollateralType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseCollateralType;

import java.util.List;

public interface CollateralRequest {

       ResponseCollateralType createCollateralType(CreateCollateralType createCollateralType);

       List<ResponseCollateralType> getAllCollateralTypes();

}
