package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response;


import com.mbankingloan.mbankingloan.Domain.Customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public record ResponseLoanAccount(String actNo,
                                  BigDecimal balance,
                                  Boolean isActive,
                                  Boolean isDeleted,
                                  LocalDate CreatedAt,
                                  List<Customer> customer

) {
}