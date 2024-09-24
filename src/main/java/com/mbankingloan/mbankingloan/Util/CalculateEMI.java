package com.mbankingloan.mbankingloan.Util;


import com.mbankingloan.mbankingloan.Domain.LoanApplication;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Request.CreateLoanApplication;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Component

public class CalculateEMI {

    public BigDecimal CalculateEMI(CreateLoanApplication createLoanApplication, LoanApplication loanApplication) {

        // Convert annual interest rate to monthly interest rate
        BigDecimal monthlyInterestRate = createLoanApplication.interestRate().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_EVEN) // Convert annual percentage rate to decimal
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_EVEN); // Convert annual rate to monthly rate

        // Calculate the principal amount after down payment
        BigDecimal principalAmount = createLoanApplication.requestAmount().subtract(loanApplication.getDownPayment());

        // EMI formula: [P * r * (1 + r)^n] / [(1 + r)^n - 1]
        BigDecimal onePlusRPowerN = BigDecimal.ONE.add(monthlyInterestRate).pow(createLoanApplication.tenure());
        BigDecimal numerator = principalAmount.multiply(monthlyInterestRate).multiply(onePlusRPowerN);
        BigDecimal denominator = onePlusRPowerN.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 2, RoundingMode.HALF_EVEN);

    }
}
