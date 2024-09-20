package com.mbankingloan.mbankingloan.Feature.Admin.Controller;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.CollateralRequest;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateCollateralType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseCollateralType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/collateralType")
public class CollateralControllerAdmin {

    private final CollateralRequest collateralRequest;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllCollateralTypes")
    List<ResponseCollateralType> getAllCollateralTypes() {
        return collateralRequest.getAllCollateralTypes();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createCollateralType")
    ResponseCollateralType createLoanType(@Valid @RequestBody CreateCollateralType createCollateral) {
       return collateralRequest.createCollateralType(createCollateral);
    }




}
