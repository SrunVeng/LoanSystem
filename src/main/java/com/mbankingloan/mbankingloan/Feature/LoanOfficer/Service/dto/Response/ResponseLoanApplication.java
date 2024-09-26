package com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Response;


import com.mbankingloan.mbankingloan.Domain.User;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
        Boolean isDrawDown,
        List<ResponseUser> users
) {
}
