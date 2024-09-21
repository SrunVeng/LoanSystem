package com.mbankingloan.mbankingloan.Feature.Admin.Service;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateBranch;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.DeleteBranch;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RecoverBranch;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseBranch;
import jakarta.validation.Valid;

import java.util.List;

public interface BranchRequest {

       ResponseBranch createBranch(CreateBranch createBranch);

       List<ResponseBranch> getAllBranch();

    ResponseBranch deleteBranch(@Valid DeleteBranch deleteBranch);

    ResponseBranch recoverBranch(@Valid RecoverBranch recoverBranch);

    ResponseBranch getBranchById(int id);
}
