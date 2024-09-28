package com.mbankingloan.mbankingloan.Feature.LoanOfficer.Controller;


import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.LoanApplicationService;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Request.CreateLoanApplication;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Response.ResponseLoanApplication;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/loanOfficer/api/v1/loanApplication")
public class LoanApplicationControllerLoanOfficer {

    private final LoanApplicationService loanApplicationService;
    private final UserService userService;


    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_LOAN-OFFICER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    ResponseLoanApplication createLoanApplication(@Valid @RequestBody CreateLoanApplication createLoanApplication, Principal principal) {
        String StaffId = principal.getName();
        return loanApplicationService.createLoanApplication(createLoanApplication,StaffId);
    }




}




