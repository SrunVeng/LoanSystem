package com.mbankingloan.mbankingloan.Feature.Management.Controller;


import com.mbankingloan.mbankingloan.Feature.Management.Service.dto.LoanManagementService;
import com.mbankingloan.mbankingloan.Feature.Management.Service.dto.Response.ResponseLoanApplicationDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bm/api/v1/loan")
public class LoanControllerBM {

    private final LoanManagementService loanManagementService;


    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_LOAN-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllLoanDetails")
    List<ResponseLoanApplicationDetails> getAllLoanApplicationDetails() {
        return loanManagementService.getAllLoanApplicationDetails();
    }

    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_LOAN-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getLoanDetailsById")
    ResponseLoanApplicationDetails getLoanApplicationDetailsById(@PathVariable int id) {
        return loanManagementService.getLoanApplicationDetailsById(id);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/approveById/{id}")
    void ApproveLoanById(@PathVariable int id) {
        loanManagementService.bmApproveLoanById(id);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/rejectById/{id}")
    void rejectById(@PathVariable int id) {
        loanManagementService.bmRejectById(id);
    }


}
