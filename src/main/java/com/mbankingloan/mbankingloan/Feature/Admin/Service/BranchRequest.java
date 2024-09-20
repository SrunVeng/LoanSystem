package com.mbankingloan.mbankingloan.Feature.Admin.Service;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateBranch;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseBranch;

import java.util.List;

public interface BranchRequest {

       ResponseBranch createBranch(CreateBranch createBranch);

       List<ResponseBranch> getAllBranch();

}
