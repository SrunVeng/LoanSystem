package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Controller;


import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.CustomerService;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.LoanAccountService;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.CreateCustomerCiF;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.CreateLoanAccount;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.DrawDownLoanAccount;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.UpdateCustomer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseCustomer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseCustomerDetails;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseLoanAccount;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/csaOfficer/api/v1/loanAccount")
public class LoanAccountControllerCSA {

    private final LoanAccountService loanAccountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    ResponseLoanAccount createLoanAccount(@Valid @RequestBody CreateLoanAccount createLoanAccount) {
        return loanAccountService.createLoanAccount(createLoanAccount);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/deleteById")
    ResponseLoanAccount deleteLoanAccountById(@PathVariable Integer Id) {
        return loanAccountService.deleteLoanAccount(Id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/drawDownLoan")
    ResponseLoanAccount drawDownLoan(@Valid @RequestBody DrawDownLoanAccount drawDownLoanAccount) {
        return loanAccountService.drawDownLoan(drawDownLoanAccount);
    }





}
