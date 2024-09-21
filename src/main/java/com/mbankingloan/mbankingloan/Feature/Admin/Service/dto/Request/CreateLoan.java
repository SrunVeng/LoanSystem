package com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request;


import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;


public record CreateLoan(

        @NotBlank(message = "Name is required")
        String name,

        BigDecimal amountLimit,
        List<Integer> collateralTypesId,

        BigDecimal moa,

        Integer tenure,

        BigDecimal interestRate,

        Integer loanTypeId



) {
}
