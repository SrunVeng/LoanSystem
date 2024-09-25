package com.mbankingloan.mbankingloan.Feature.Admin.Controller;



import com.mbankingloan.mbankingloan.Feature.Admin.Service.LoanRequest;

import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoanAccountType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoanType;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/v1/loan")
public class LoanControllerAllStaffPermit {

    private final LoanRequest loanRequest;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'LOAN-OFFICER', 'CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllLoans")
    List<ResponseLoan> getAllLoans() {
        return loanRequest.getAllLoans();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'LOAN-OFFICER', 'CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getLoanById/{id}")
    ResponseLoan getLoanById(@PathVariable int id) {
        return loanRequest.getLoanById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'LOAN-OFFICER', 'CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getLoanTypeById/{id}")
    ResponseLoanType getLoanTypeById(@PathVariable int id) {
        return loanRequest.getLoanTypeById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'LOAN-OFFICER', 'CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllLoanTypes")
    List<ResponseLoanType> getAllLoanTypes() {
        return loanRequest.getAllLoanTypes();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'LOAN-OFFICER', 'CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllLoanAccountTypes")
    List<ResponseLoanAccountType> getAllLoanAccountTypes() {
        return loanRequest.getAllLoanAccountTypes();
    }


//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/createLoanType")
//    ResponseLoanType createLoanType(@Valid @RequestBody CreateLoanType createLoanType) {
//        return loanRequest.createLoanType(createLoanType);
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/createLoan")
//    ResponseLoan createLoan(@Valid @RequestBody CreateLoan createLoan) {
//        return loanRequest.createLoan(createLoan);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping("/deleteLoan")
//    ResponseLoan deleteLoanById(@Valid @RequestBody DeleteLoan deleteLoan) {
//        return loanRequest.deleteLoan(deleteLoan);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping("/deleteLoanType")
//    ResponseLoanType deleteLoanTypeById(@Valid @RequestBody DeleteLoan deleteLoan) {
//        return loanRequest.deleteLoanType(deleteLoan);
//    }
//
//
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping("/recoverLoanById/{id}")
//    ResponseLoan recoverLoan(@PathVariable int id) {
//        return loanRequest.recoverLoanById(id);
//    }
//
//
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping("/recoverLoanTypeById/{id}")
//    ResponseLoanType recoverLoanTypeById(@PathVariable int id) {
//        return loanRequest.recoverLoanTypeById(id);
//    }



}
