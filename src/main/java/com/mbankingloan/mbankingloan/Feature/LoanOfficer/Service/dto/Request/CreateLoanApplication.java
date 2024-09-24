package com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CreateLoanApplication(
        @NotNull(message = "LoanType ID is Require")
        Integer loanTypeId,
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
