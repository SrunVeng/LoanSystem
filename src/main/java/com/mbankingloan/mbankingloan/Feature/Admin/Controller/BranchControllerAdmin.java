package com.mbankingloan.mbankingloan.Feature.Admin.Controller;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.BranchRequest;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateBranch;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseBranch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/branch")
public class BranchControllerAdmin {

    private final BranchRequest branchRequest;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllBranches")
    List<ResponseBranch> getAllBranches() {
        return branchRequest.getAllBranch();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createBranch")
    ResponseBranch createBranch(@Valid @RequestBody CreateBranch createBranch) {
        return  branchRequest.createBranch(createBranch);
    }







}
