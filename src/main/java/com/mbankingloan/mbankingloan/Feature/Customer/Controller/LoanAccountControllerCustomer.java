package com.mbankingloan.mbankingloan.Feature.Customer.Controller;


import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.LoanAccountCustomerService;
import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Request.CheckLoanAccount;
import com.mbankingloan.mbankingloan.Feature.Customer.Service.dto.Response.ResponseLoanAccountDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/api/v1/loanAccount")
public class LoanAccountControllerCustomer {

    private LoanAccountCustomerService loanAccountCustomerService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/checkLoanAccount")
    ResponseLoanAccountDetails checkLoanAccount(@Valid @RequestBody CheckLoanAccount checkLoanAccount) {
        return loanAccountCustomerService.checkLoanAccount(checkLoanAccount);
    }




}
