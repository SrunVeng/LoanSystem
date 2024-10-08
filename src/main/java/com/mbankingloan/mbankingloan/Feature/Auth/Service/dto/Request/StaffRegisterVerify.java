package com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StaffRegisterVerify(

        @NotNull(message = "Email is required")
        @Email
        String email,
        @NotBlank
        String verificationCode,
        @NotBlank(message = "newPassword is required")
        String newPassword,
        @NotBlank(message = "Confirm Password is required")
        String confirmPassword

) {
}
