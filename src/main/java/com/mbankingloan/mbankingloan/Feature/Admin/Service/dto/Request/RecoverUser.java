package com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record RecoverUser(

        @NotNull(message = "ID is required")
        Integer id,
        @NotBlank(message = "FirstName is required")
        String firstName,
        @NotBlank(message = "LastName is required")
        String lastName


) {
}
