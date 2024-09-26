package com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Response;


import lombok.Builder;

@Builder
public record JwtResponse(
        String tokenType,
        String accessToken,
        String refreshToken
) {
}
