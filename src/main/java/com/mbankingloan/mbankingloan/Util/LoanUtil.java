package com.mbankingloan.mbankingloan.Util;


import com.mbankingloan.mbankingloan.Domain.CollateralType;
import com.mbankingloan.mbankingloan.Domain.Loan;
import com.mbankingloan.mbankingloan.Domain.LoanApplication;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.CollateralTypeRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanRepository;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Request.CreateLoanApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Component
@RequiredArgsConstructor
public class LoanUtil {

    private final LoanRepository loanRepository;
    private final CollateralTypeRepository collateralTypeRepository;


    public BigDecimal CalculateEMI(CreateLoanApplication createLoanApplication, LoanApplication loanApplication) {

            // Convert annual interest rate to monthly interest rate
        BigDecimal monthlyInterestRate = createLoanApplication.interestRate()
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_EVEN) // Convert annual percentage rate to decimal
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_EVEN); // Convert annual rate to monthly rate

            // Use the request amount as the principal amount directly
        BigDecimal principalAmount = createLoanApplication.requestAmount(); // Removed down payment logic

            // EMI formula: [P * r * (1 + r)^n] / [(1 + r)^n - 1]
        BigDecimal onePlusRPowerN = BigDecimal.ONE.add(monthlyInterestRate).pow(createLoanApplication.tenure());
        BigDecimal numerator = principalAmount.multiply(monthlyInterestRate).multiply(onePlusRPowerN);
        BigDecimal denominator = onePlusRPowerN.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 2, RoundingMode.HALF_EVEN);

    }


    public BigDecimal setMoaBasedOnCollateralTypes(CreateLoanApplication createLoanApplication) {

        CollateralType fixDepositType = collateralTypeRepository.findById(3)
                .orElseThrow(() -> new IllegalArgumentException("CollateralType with ID 3 not found."));
        BigDecimal fixDepositMoa = fixDepositType.getMoa();


        CollateralType hardTitle = collateralTypeRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("CollateralType with ID 1 not found."));
        BigDecimal hardTitleMoa = hardTitle.getMoa();


        CollateralType softTitle = collateralTypeRepository.findById(2)
                .orElseThrow(() -> new IllegalArgumentException("CollateralType with ID 2 not found."));
        BigDecimal softTitleMoa = softTitle.getMoa();

        // Then check the collateral IDs
        if (createLoanApplication.collateralID().contains(3)) {
            return fixDepositMoa;
        } else if (createLoanApplication.collateralID().contains(1)) {
            return hardTitleMoa;
        } else if (createLoanApplication.collateralID().contains(2)) {
            return softTitleMoa;
        } else {
            // Optionally, set a default value or handle the case where no collateral type matches
            return BigDecimal.ZERO;
        }


    }


    public Loan getLoanByAmount(BigDecimal amount) {

        Loan personalLoan = loanRepository.findById(2).orElseThrow();
        Loan microBusinessLoan = loanRepository.findById(3).orElseThrow();
        Loan smallBusinessLoan = loanRepository.findById(4).orElseThrow();
        Loan mediumBusinessLoan = loanRepository.findById(5).orElseThrow();
        Loan largeBusinessLoan = loanRepository.findById(6).orElseThrow();
        Loan commercialBusinessLoan = loanRepository.findById(7).orElseThrow();

        if (amount.compareTo(new BigDecimal("20000")) >= 0 && amount.compareTo(new BigDecimal("49999")) <= 0) {
            return personalLoan;
        } else if (amount.compareTo(new BigDecimal("50000")) >= 0 && amount.compareTo(new BigDecimal("99999")) <= 0) {
            return microBusinessLoan;
        } else if (amount.compareTo(new BigDecimal("100000")) >= 0 && amount.compareTo(new BigDecimal("499999")) <= 0) {
            return smallBusinessLoan;
        } else if (amount.compareTo(new BigDecimal("500000")) >= 0 && amount.compareTo(new BigDecimal("999999")) <= 0) {
            return mediumBusinessLoan;
        } else if (amount.compareTo(new BigDecimal("1000000")) >= 0 && amount.compareTo(new BigDecimal("1999999")) <= 0) {
            return commercialBusinessLoan;
        } else if (amount.compareTo(new BigDecimal("2000000")) >= 0) {
            return largeBusinessLoan;
        } else {
            throw new IllegalArgumentException("Amount not within any defined loan range.");
        }
    }


}
