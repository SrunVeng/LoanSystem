package com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto;

import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Request.CreateLoanApplication;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Response.ResponseLoanApplication;

public interface UserService {

    Integer findUserIdByStaffId(String StaffId);

}
