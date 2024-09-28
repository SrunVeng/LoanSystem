package com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CreateLoanApplication(
        @NotNull(message = "Loan ID is Require")
        Integer loanId,

        @DecimalMin(value = "20000.00", message = "Amount must be at least 20000")
        @DecimalMax(value = "1000000.00", message = "Amount must not exceed 1000000")
        BigDecimal requestAmount,


        @NotNull(message = "Tenure ID is Require")
        Integer tenure,

        @NotNull(message = "InterestRate is Require")
        BigDecimal interestRate,

        @NotNull(message = "CustomerCiF is Require")
        List<String> customerCifNumber,

        @NotNull(message = "CollateralID is Require")
        List<Integer> collateralID,

        @NotNull(message = "TotalCollateralValue is Require")
        BigDecimal totalCollateralValue


) {
}
