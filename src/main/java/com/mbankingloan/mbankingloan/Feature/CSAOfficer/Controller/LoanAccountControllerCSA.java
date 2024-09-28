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

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/csaOfficer/api/v1/loanAccount")
public class LoanAccountControllerCSA {

    private final LoanAccountService loanAccountService;

    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    ResponseLoanAccount createLoanAccount(@Valid @RequestBody CreateLoanAccount createLoanAccount, Principal principal) {
        String staffId = principal.getName();
        return loanAccountService.createLoanAccount(createLoanAccount,staffId);
    }

    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/deleteById/{Id}")
    ResponseLoanAccount deleteLoanAccountById(@PathVariable Integer Id,Principal principal) {
        String staffId = principal.getName();
        return loanAccountService.deleteLoanAccount(Id,staffId);
    }


    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/drawDownLoan")
    ResponseLoanAccount drawDownLoan(@Valid @RequestBody DrawDownLoanAccount drawDownLoanAccount,Principal principal) {
        String staffId = principal.getName();
        return loanAccountService.drawDownLoan(drawDownLoanAccount,staffId);
    }





}
