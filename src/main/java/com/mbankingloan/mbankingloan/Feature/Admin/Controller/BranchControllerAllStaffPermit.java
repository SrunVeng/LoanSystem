package com.mbankingloan.mbankingloan.Feature.Admin.Controller;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.BranchRequest;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseBranch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/v1/branch")
public class BranchControllerAllStaffPermit {

    private final BranchRequest branchRequest;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'LOAN-OFFICER', 'CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllBranches")
    List<ResponseBranch> getAllBranches() {
        return branchRequest.getAllBranch();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'LOAN-OFFICER', 'CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getBrancheById")
    ResponseBranch getAllBranchById(@PathVariable int id) {
        return branchRequest.getBranchById(id);
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/createBranch")
//    ResponseBranch createBranch(@Valid @RequestBody CreateBranch createBranch) {
//        return  branchRequest.createBranch(createBranch);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping("/deleteBranch")
//    ResponseBranch deleteBranch(@Valid @RequestBody DeleteBranch deleteBranch) {
//        return  branchRequest.deleteBranch(deleteBranch);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping("/recoverBranch")
//    ResponseBranch recoverBranch(@Valid @RequestBody RecoverBranch recoverBranch) {
//        return  branchRequest.recoverBranch(recoverBranch);
//    }







}
