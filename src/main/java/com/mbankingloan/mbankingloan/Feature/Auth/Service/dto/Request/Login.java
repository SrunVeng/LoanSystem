package com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request;

import jakarta.validation.constraints.NotBlank;

public record Login(
        @NotBlank(message = "StaffId is required")
        String StaffId,
        @NotBlank(message = "Password is required")
        String password
) {
}
