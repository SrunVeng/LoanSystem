package com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DeleteLoanType(

        @NotNull(message = "ID is required") Integer id,

        @NotBlank(message = "Name is required") String name


) {
}
