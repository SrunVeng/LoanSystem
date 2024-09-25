package com.mbankingloan.mbankingloan.Feature.Auth.Controller;


import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request.StaffRegisterVerify;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.VerifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/api/v1/verify")
public class VerifyControllerAllStaffPermit {

    private final VerifyService verifyService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'LOAN-OFFICER', 'CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/email")
    void verify(@Valid @RequestBody StaffRegisterVerify staffRegisterVerify) {
        verifyService.registerVerify(staffRegisterVerify);

    }


    }
