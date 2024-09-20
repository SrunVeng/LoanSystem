package com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request;

import jakarta.validation.constraints.NotBlank;




public record CreateLoanType(
        @NotBlank(message = "Name is required")
        String name


) {
}
