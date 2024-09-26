package com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerRegisterVerify(

        @NotNull(message = "Email is required")
        @Email
        String email,
        @NotBlank
        String verificationCode,
        @NotBlank(message = "newPassword is required")
        String newPassword,
        @NotBlank(message = "Confirm Password is required")
        String confirmPassword,

        @NotBlank(message = "Pin is required")
        @Size(min = 4, max = 6)
        String Pin

) {
}
