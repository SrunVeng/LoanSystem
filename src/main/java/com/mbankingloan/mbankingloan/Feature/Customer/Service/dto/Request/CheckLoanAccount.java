package com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Request;

import jakarta.validation.constraints.NotBlank;

public record CheckLoanAccount(
        @NotBlank(message = "Pin is required")
        String pin

) {
}
