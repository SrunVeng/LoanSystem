package com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Response;


import com.mbankingloan.mbankingloan.Domain.Customer;
import com.mbankingloan.mbankingloan.Domain.LoanAccountType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public record ResponseLoanAccountDetails(String actNo,
                                         String phoneNumber,
                                         BigDecimal balance,
                                         Boolean isActive,
                                         Boolean isDeleted,
                                         LocalDate CreatedAt,
                                         LoanAccountType loanAccountType,
                                         List<Customer> customer

) {
}