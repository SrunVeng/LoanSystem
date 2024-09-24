package com.mbankingloan.mbankingloan.Feature.LoanOfficer.Controller;


import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.LoanApplicationService;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Request.CreateLoanApplication;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Response.ResponseLoanApplication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/loanOfficer/api/v1/loanApplication")
public class LoanApplicationControllerLoanOfficer {

    private final LoanApplicationService loanApplicationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    ResponseLoanApplication createLoanApplication(@Valid @RequestBody CreateLoanApplication createLoanApplication){
        return loanApplicationService.createLoanApplication(createLoanApplication);
    }




}




