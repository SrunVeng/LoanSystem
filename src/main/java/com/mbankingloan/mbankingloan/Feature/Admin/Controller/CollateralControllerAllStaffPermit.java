package com.mbankingloan.mbankingloan.Feature.Admin.Controller;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.CollateralRequest;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseCollateralType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/v1/collateralType")
public class CollateralControllerAllStaffPermit {

    private final CollateralRequest collateralRequest;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'LOAN-OFFICER', 'CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllCollateralTypes")
    List<ResponseCollateralType> getAllCollateralTypes() {
        return collateralRequest.getAllCollateralTypes();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'LOAN-OFFICER', 'CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getCollateralTypeById/{id}")
    ResponseCollateralType getCollateralTypeById(@PathVariable int id) {
        return collateralRequest.getCollateralTypeById(id);
    }

//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/createCollateralType")
//    ResponseCollateralType createLoanType(@Valid @RequestBody CreateCollateralType createCollateral) {
//       return collateralRequest.createCollateralType(createCollateral);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping("/deleteCollateralType")
//    ResponseCollateralType deleteLoanType(@Valid @RequestBody DeleteCollateralType deleteCollateral) {
//        return collateralRequest.deleteCollateralType(deleteCollateral);
//    }
//
//    // add more not just ID fix later
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping("/recoverCollateralType")
//    ResponseCollateralType recoverCollateralType(@Valid @RequestBody RecoverCollateralType recoverCollateral) {
//        return collateralRequest.recoverCollateralType(recoverCollateral);
//    }
//



}
