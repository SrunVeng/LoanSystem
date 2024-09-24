package com.mbankingloan.mbankingloan.Util;


import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Request.CreateLoanApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class MoaUtil {

    @Value("${moa.100}")
    private BigDecimal moa100;
    @Value("${moa.80}")
    private BigDecimal moa80;
    @Value("${moa.50}")
    private BigDecimal moa50;


    public BigDecimal setMoaBasedOnCollateralTypes(CreateLoanApplication createLoanApplication) {
        // Check if the loan type is 3 first
        if (createLoanApplication.loanTypeId() == 3) {
            return moa100;
        }

        // Then check the collateral IDs
        if (createLoanApplication.collateralID().contains(3)) {
            return moa100;
        } else if (createLoanApplication.collateralID().contains(1)) {
            return moa80;
        } else if (createLoanApplication.collateralID().contains(2)) {
            return moa50;
        } else {
            // Optionally, set a default value or handle the case where no collateral type matches
            return BigDecimal.ZERO;
        }


    }

    public BigDecimal setDownPaymentBasedOnLoanType(CreateLoanApplication createLoanApplication) {

        return switch (createLoanApplication.loanTypeId()) {
            case 1, 2 -> BigDecimal.ZERO;
            case 3 -> createLoanApplication.requestAmount().multiply(BigDecimal.valueOf(0.3));
            default ->
                // Optionally, set a default down payment or handle unexpected loan types
                    BigDecimal.ZERO; // or any other default value you prefer
        };
    }
}
