package com.mbankingloan.mbankingloan.Feature.Admin.Service;


import com.mbankingloan.mbankingloan.Domain.Branch;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.BranchRepository;

import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateBranch;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseBranch;

import com.mbankingloan.mbankingloan.Mapper.BranchMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BranchRequestImpl implements BranchRequest {


    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    public ResponseBranch createBranch(CreateBranch createBranch) {

        if(branchRepository.existsByname(createBranch.name())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Branch name already exists");
        }
        if(branchRepository.existsBycode(createBranch.code())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Branch code already exists");
        }

        Branch newBranch = branchMapper.fromBranchRequest(createBranch);
        branchRepository.save(newBranch);


        return branchMapper.toBranchResponse(newBranch);

    }


    // View ALl Branches
    @Override
    public List<ResponseBranch> getAllBranch() {
        return branchMapper.toBranchResponseList(branchRepository.findAll());
    }


}
