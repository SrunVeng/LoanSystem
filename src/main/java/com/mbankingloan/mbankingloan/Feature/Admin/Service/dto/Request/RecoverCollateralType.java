package com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record RecoverCollateralType(

        @NotNull(message = "ID is required")
        Integer id


) {
}
