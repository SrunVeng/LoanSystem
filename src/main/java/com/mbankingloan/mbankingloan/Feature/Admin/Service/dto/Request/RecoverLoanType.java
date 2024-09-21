package com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request;

import jakarta.validation.constraints.NotNull;


public record RecoverLoanType(

        @NotNull(message = "ID is required")
        Integer id

) {
}
