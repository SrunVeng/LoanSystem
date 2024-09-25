package com.mbankingloan.mbankingloan.Feature.Auth.Service.dto;

import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request.StaffRegisterVerify;
import jakarta.validation.Valid;

public interface VerifyService {

    void registerVerify(@Valid StaffRegisterVerify staffRegisterVerify);
}
