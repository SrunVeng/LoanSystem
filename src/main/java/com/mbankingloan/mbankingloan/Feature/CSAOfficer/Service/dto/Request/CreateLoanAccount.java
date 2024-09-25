package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request;

import com.mbankingloan.mbankingloan.Domain.LoanApplication;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public record CreateLoanAccount(


        @NotNull(message = "CustomerCif is required")
        List<String> CustomerCif,

        @NotNull(message = "LoanApplicationID is required")
        Integer loanApplicationId,

        @NotBlank(message = "Phone Number is required")
        String phoneNumber

) {
}
