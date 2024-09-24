package com.mbankingloan.mbankingloan.Feature.Management.Service.dto.Response;

import com.mbankingloan.mbankingloan.Domain.CollateralType;
import com.mbankingloan.mbankingloan.Domain.Customer;
import com.mbankingloan.mbankingloan.Domain.LoanType;
import com.mbankingloan.mbankingloan.Domain.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ResponseLoanApplicationDetails(

        Integer id,
        LoanType loanType,
     //   List<User> users,
        List<Customer> customer,
        BigDecimal amount,
        Integer tenure,
        List<CollateralType> collateralTypes,
        BigDecimal interestRate,
        LocalDate maturityDate,
        BigDecimal monthlyInstallment,
        BigDecimal moa,
        BigDecimal downPayment,
        Boolean isApprovedByBranchManager,
        Boolean isApprovedByHeadOfLoan,
        Boolean isDrawDown


) {
}
