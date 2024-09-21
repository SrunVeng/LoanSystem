package com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response;


import com.mbankingloan.mbankingloan.Domain.CollateralType;
import com.mbankingloan.mbankingloan.Domain.LoanType;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


public record ResponseLoan(
        Integer id,
        String name,

        BigDecimal amountLimit,

        List<CollateralType> collateralTypes,


        BigDecimal interestRate,

        Boolean isDeleted,

        LoanType loantype
) {
}
