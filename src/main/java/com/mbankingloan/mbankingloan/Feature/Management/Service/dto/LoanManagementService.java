package com.mbankingloan.mbankingloan.Feature.Management.Service.dto;


import com.mbankingloan.mbankingloan.Feature.Management.Service.dto.Response.ResponseLoanApplicationDetails;

import java.util.List;

public interface LoanManagementService {
    
    
    List<ResponseLoanApplicationDetails> getAllLoanApplicationDetails();


    void bmApproveLoanById(int id);

    void bmRejectById(int id);

    void headRejectById(int id);

    void headApproveLoanById(int id);

    ResponseLoanApplicationDetails getLoanApplicationDetailsById(int id);
}
