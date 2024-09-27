package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Controller;



import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.LoanAccountService;

import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.CreateLoanAccount;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.DrawDownLoanAccount;

import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseLoanAccount;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/csaOfficer/api/v1/loanAccount")
public class LoanAccountControllerCSA {

    private final LoanAccountService loanAccountService;

    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    ResponseLoanAccount createLoanAccount(@Valid @RequestBody CreateLoanAccount createLoanAccount) {
        return loanAccountService.createLoanAccount(createLoanAccount);
    }

    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/deleteById/{Id}")
    ResponseLoanAccount deleteLoanAccountById(@PathVariable Integer Id) {
        return loanAccountService.deleteLoanAccount(Id);
    }


    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/drawDownLoan")
    ResponseLoanAccount drawDownLoan(@Valid @RequestBody DrawDownLoanAccount drawDownLoanAccount) {
        return loanAccountService.drawDownLoan(drawDownLoanAccount);
    }





}
