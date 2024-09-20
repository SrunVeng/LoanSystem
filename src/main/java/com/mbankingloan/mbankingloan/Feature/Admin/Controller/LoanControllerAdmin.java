package com.mbankingloan.mbankingloan.Feature.Admin.Controller;



import com.mbankingloan.mbankingloan.Feature.Admin.Service.LoanRequest;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateLoanType;

import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoanType;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/loan")
public class  LoanControllerAdmin {

    private final LoanRequest loanRequest;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllLoans")
    List<ResponseLoan> getAllLoans() {
        return loanRequest.getAllLoans();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllLoanTypes")
    List<ResponseLoanType> getAllLoanTypes() {
        return loanRequest.getAllLoanTypes();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createLoanType")
    ResponseLoanType createLoanType(@Valid @RequestBody CreateLoanType createLoanType) {
        return loanRequest.createLoanType(createLoanType);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createLoan")
    ResponseLoan createLoan(@Valid @RequestBody CreateLoan createLoan) {
        return loanRequest.createLoan(createLoan);
    }





}
