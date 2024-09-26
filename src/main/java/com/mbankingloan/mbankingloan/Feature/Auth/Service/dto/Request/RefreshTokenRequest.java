package com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "Refresh token is required")
        String refreshToken
) {
}
