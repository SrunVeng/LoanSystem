package com.mbankingloan.mbankingloan.Feature.Management.Service.dto;


import com.mbankingloan.mbankingloan.Domain.LoanApplication;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanApplicationRepository;
import com.mbankingloan.mbankingloan.Feature.Management.Service.dto.Response.ResponseLoanApplicationDetails;
import com.mbankingloan.mbankingloan.Mapper.LoanApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanManagementServiceImpl implements LoanManagementService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanApplicationMapper loanApplicationMapper;


    @Override
    public List<ResponseLoanApplicationDetails> getAllLoanApplicationDetails() {
        return loanApplicationMapper.toResponseLoanApplicationDetailsList(loanApplicationRepository.findAll());
    }

    @Override
    @Transactional
    public void bmApproveLoanById(int id) {

        if (!loanApplicationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "LoanApplication Does not exist");
        }
        LoanApplication loanApplication = loanApplicationRepository.findById(id).orElseThrow();
        loanApplication.setIsApprovedByBranchManager(true);
    }

    @Override
    @Transactional
    public void bmRejectById(int id) {
        if (!loanApplicationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "LoanApplication Does not exist");
        }
        LoanApplication loanApplication = loanApplicationRepository.findById(id).orElseThrow();
        loanApplication.setIsRejectedByBM(true);

    }

    @Override
    @Transactional
    public void headRejectById(int id) {
        if (!loanApplicationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "LoanApplication Does not exist");
        }
        LoanApplication loanApplication = loanApplicationRepository.findById(id).orElseThrow();
        loanApplication.setIsRejectedByHeadOfLoan(true);

    }

    @Override
    @Transactional
    public void headApproveLoanById(int id) {
        if (!loanApplicationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "LoanApplication Does not exist");
        }
        LoanApplication loanApplication = loanApplicationRepository.findById(id).orElseThrow();
        loanApplication.setIsApprovedByHeadOfLoan(true);
    }

    @Override
    public ResponseLoanApplicationDetails getLoanApplicationDetailsById(int id) {
        if (!loanApplicationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "LoanApplication Does not exist");
        }
        LoanApplication loanApplication = loanApplicationRepository.findById(id).orElseThrow();

        return loanApplicationMapper.toResponseLoanApplicationDetails(loanApplication);
    }


}
