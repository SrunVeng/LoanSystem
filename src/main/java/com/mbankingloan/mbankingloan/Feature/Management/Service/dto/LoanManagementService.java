package com.mbankingloan.mbankingloan.Feature.Management.Service.dto;


import com.mbankingloan.mbankingloan.Feature.Management.Service.dto.Response.ResponseLoanApplicationDetails;

import java.util.List;

public interface LoanManagementService {
    
    
    List<ResponseLoanApplicationDetails> getAllLoanApplicationDetails();


    void bmApproveLoanById(int id, String StaffId);

    void bmRejectById(int id,String StaffId);

    void headRejectById(int id,String StaffId);

    void headApproveLoanById(int id, String StaffId);

    ResponseLoanApplicationDetails getLoanApplicationDetailsById(int id);
}
