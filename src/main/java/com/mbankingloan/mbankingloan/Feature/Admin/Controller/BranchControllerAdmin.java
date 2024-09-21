package com.mbankingloan.mbankingloan.Feature.Admin.Controller;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.BranchRequest;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateBranch;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.DeleteBranch;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RecoverBranch;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseBranch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/v1/branch")
public class BranchControllerAdmin {

    private final BranchRequest branchRequest;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllBranches")
    List<ResponseBranch> getAllBranches() {
        return branchRequest.getAllBranch();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getBrancheById")
    ResponseBranch getAllBranchById(@PathVariable int id) {
        return branchRequest.getBranchById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createBranch")
    ResponseBranch createBranch(@Valid @RequestBody CreateBranch createBranch) {
        return  branchRequest.createBranch(createBranch);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/deleteBranch")
    ResponseBranch deleteBranch(@Valid @RequestBody DeleteBranch deleteBranch) {
        return  branchRequest.deleteBranch(deleteBranch);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/recoverBranch")
    ResponseBranch recoverBranch(@Valid @RequestBody RecoverBranch recoverBranch) {
        return  branchRequest.recoverBranch(recoverBranch);
    }







}
