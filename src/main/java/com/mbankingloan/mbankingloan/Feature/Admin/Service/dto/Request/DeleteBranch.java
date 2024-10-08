package com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DeleteBranch(

        @NotNull(message = "ID is required")
        Integer id,

        @NotBlank(message = "Code is required")
        String code


) {
}
