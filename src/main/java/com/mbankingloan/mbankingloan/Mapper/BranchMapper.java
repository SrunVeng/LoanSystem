package com.mbankingloan.mbankingloan.Mapper;


import com.mbankingloan.mbankingloan.Domain.Branch;

import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateBranch;

import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseBranch;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    Branch fromBranchRequest(CreateBranch createBranch);
    ResponseBranch toBranchResponse(Branch branch);

    List<ResponseBranch> toBranchResponseList(List<Branch> branches);

}
