package com.mbankingloan.mbankingloan.Feature.Customer.Controller;


import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.LoanAccountCustomerService;
import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Request.CheckLoanAccount;
import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Response.ResponseLoanAccountDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/api/v1/loanAccount")
public class LoanAccountControllerCustomer {

    private LoanAccountCustomerService loanAccountCustomerService;


    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/checkAllLoanAccount")
    List<ResponseLoanAccountDetails> checkAllLoanAccount(@Valid @RequestBody CheckLoanAccount checkLoanAccount, Principal principal) {
        String username = principal.getName();
        return loanAccountCustomerService.checkLoanAccount(checkLoanAccount,username);


    }




}
