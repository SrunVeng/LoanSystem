package com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Response;


import java.math.BigDecimal;
import java.time.LocalDate;

public record ResponseLoanApplication(
        Integer id,
        BigDecimal requestAmount,
        BigDecimal maxLoanableAmount,
        Integer tenure,
        BigDecimal interestRate,
        LocalDate maturityDate,
        LocalDate createdAt,
        BigDecimal monthlyInstallment,
        BigDecimal moa,
        BigDecimal downPayment,
        Boolean isApprovedByBranchManager,
        Boolean isApprovedByHeadOfLoan,
        Boolean isDrawDown
) {
}
