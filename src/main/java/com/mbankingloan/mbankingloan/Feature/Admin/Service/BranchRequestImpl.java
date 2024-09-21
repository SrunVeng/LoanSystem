package com.mbankingloan.mbankingloan.Feature.Admin.Service;


import com.mbankingloan.mbankingloan.Domain.Branch;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.BranchRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateBranch;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.DeleteBranch;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RecoverBranch;
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

        if (branchRepository.existsByName(createBranch.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Branch name already exists");
        }
        if (branchRepository.existsByCode(createBranch.code())) {
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

    @Override
    public ResponseBranch deleteBranch(DeleteBranch deleteBranch) {
        if (!branchRepository.existsById(deleteBranch.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan ID not found");
        }
        if (!branchRepository.existsByCode(deleteBranch.code())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Branch Code not found");
        }

        Branch branch = branchRepository.findByIdAndCode(deleteBranch.id(), deleteBranch.code()).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Name and Code of Branch do not match"));
        branch.setIsDeleted(true);

        return branchMapper.toBranchResponse(branch);
    }

    @Override
    public ResponseBranch recoverBranch(RecoverBranch recoverBranch) {

        if (!branchRepository.existsById(recoverBranch.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan ID not found");
        }

        Branch branch = branchRepository.findById(recoverBranch.id()).orElseThrow();
        branch.setIsDeleted(false);
        return branchMapper.toBranchResponse(branch);
    }


}
