package com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request;

import jakarta.validation.constraints.NotBlank;

public record LoginCustomer(
        @NotBlank(message = "PhoneNumber is required")
        String PhoneNumber,
        @NotBlank(message = "Password is required")
        String password
) {
}
