package com.mbankingloan.mbankingloan.Feature.Management.Controller;


import com.mbankingloan.mbankingloan.Feature.Management.Service.dto.LoanManagementService;
import com.mbankingloan.mbankingloan.Feature.Management.Service.dto.Response.ResponseLoanApplicationDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/head/api/v1/loan")
public class LoanControllerHeadOfLoan {

    private final LoanManagementService loanManagementService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllLoanDetails")
    List<ResponseLoanApplicationDetails> getAllLoanApplicationDetails() {
        return loanManagementService.getAllLoanApplicationDetails();
    };


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getLoanDetailsById")
    ResponseLoanApplicationDetails getLoanApplicationDetailsById(@PathVariable int id) {
        return loanManagementService.getLoanApplicationDetailsById(id);
    };


    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/approveById/{id}")
    void approveLoanById(@PathVariable int id) {
        loanManagementService.headApproveLoanById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/rejectById/{id}")
    void rejectById(@PathVariable int id) {
        loanManagementService.headRejectById(id);
    }





}
