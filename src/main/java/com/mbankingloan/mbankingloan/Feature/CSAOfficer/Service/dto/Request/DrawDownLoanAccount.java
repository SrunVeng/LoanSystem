package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;


public record DrawDownLoanAccount(

        @NotNull(message = "CustomerCif is required")
        List<String> CustomerCif,
        @NotNull(message = "LoanAccountID is required")
        Integer loanApplicationId,
        @NotNull(message = "LoanAccountNumber is required")
        String loanAccountNumber



) {
}
