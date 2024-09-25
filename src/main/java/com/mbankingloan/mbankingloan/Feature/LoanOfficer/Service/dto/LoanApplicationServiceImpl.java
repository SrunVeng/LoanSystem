package com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto;


import com.mbankingloan.mbankingloan.Domain.CollateralType;
import com.mbankingloan.mbankingloan.Domain.Customer;
import com.mbankingloan.mbankingloan.Domain.LoanApplication;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.CollateralTypeRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanApplicationRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanTypeRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.CustomerRepository;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Request.CreateLoanApplication;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Response.ResponseLoanApplication;
import com.mbankingloan.mbankingloan.Mapper.LoanApplicationMapper;
import com.mbankingloan.mbankingloan.Util.CalculateEMI;
import com.mbankingloan.mbankingloan.Util.MoaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final CollateralTypeRepository collateralTypeRepository;
    private final CustomerRepository customerRepository;
    private final MoaUtil moaUtil;
    private final CalculateEMI calculateEMI;
    private final LoanApplicationMapper loanApplicationMapper;

    @Value("${loan.min-value}")
    private BigDecimal minLoanValue;

    @Value("${loan.max-value}")
    private BigDecimal maxLoanValue;

    @Value("${interest-rate.min-value}")
    private BigDecimal minInterestRate;

    @Value("${interest-rate.max-value}")
    private BigDecimal maxInterestRate;

    @Value("${loan-tenure.min-value}")
    private Integer minLoanTenure;

    @Value("${loan-tenure.max-value}")
    private Integer maxLoanTenure;

    @Value("${loan-type-id.range.min}")
    private Integer minLoanTypeId;

    @Value("${loan-type-id.range.max}")
    private Integer maxLoanTypeId;

    @Value("${collateralType-id.range.min}")
    private Integer minCollateralTypeId;

    @Value("${collateralType-id.range.max}")
    private Integer maxCollateralTypeId;

    @Value("${loan-bm-approve-limit}")
    private BigDecimal branchManagerApprovalLimit;


    @Override
    public ResponseLoanApplication createLoanApplication(CreateLoanApplication createLoanApplication) {

        //Declare
        List<Integer> collateralTypesId = createLoanApplication.collateralID();
        List<CollateralType> collateralTypes = collateralTypeRepository.findAllById(collateralTypesId);
        List<String> cifNumbers = createLoanApplication.customerCifNumber();
        List<Customer> existingCustomers = customerRepository.findAllByCustomerCIFNumberIn(cifNumbers);
        List<String> existingCifNumbers = existingCustomers.stream().map(Customer::getCustomerCIFNumber) // Adjust this to your actual method for getting the CIF number
                .toList();
        boolean isOutOfRangeCollateralTypeId = collateralTypesId.stream().anyMatch(type -> type < minCollateralTypeId || type > maxCollateralTypeId);
        boolean anyNonExistent = cifNumbers.stream().anyMatch(cif -> !existingCifNumbers.contains(cif));

        //End Declare
        //Validation


        if (anyNonExistent) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "One or more Customer CIF numbers do not exist.");
        }

        if (isOutOfRangeCollateralTypeId) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CollateralTypeID must be in the range of " + minCollateralTypeId + " to " + maxCollateralTypeId);
        }
        if (collateralTypesId.size() > maxCollateralTypeId) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You cannot select more than " + maxCollateralTypeId + " collateral typesID.");
        }

        if (createLoanApplication.requestAmount().compareTo(minLoanValue) < 0 || createLoanApplication.requestAmount().compareTo(maxLoanValue) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan Amount is between 20000 and 10000000");
        }

        if (createLoanApplication.interestRate().compareTo(minInterestRate) < 0 || createLoanApplication.interestRate().compareTo(maxInterestRate) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Interest rate be in Range " + minInterestRate + " to " + maxInterestRate);
        }

        if (createLoanApplication.tenure() < minLoanTenure || createLoanApplication.tenure() > maxLoanTenure) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tenure must be in Range " + minLoanTenure + " to " + maxLoanTenure);
        }

        if ((createLoanApplication.loanTypeId() < minLoanTypeId) || createLoanApplication.loanTypeId() > maxLoanTypeId) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "LoanTypeID must be in Range " + minLoanTypeId + " to " + maxLoanTypeId);
        }

        if ((createLoanApplication.loanTypeId().equals(minLoanTypeId) || createLoanApplication.loanTypeId() == 2) && createLoanApplication.tenure() > minLoanTenure) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Term Loan and OD cannot exceed " + minLoanTenure + "months.");
        }

        if (createLoanApplication.loanTypeId() == 3 && createLoanApplication.tenure() > maxLoanTenure) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Home Loan cannot exceed " + maxLoanTenure + "months.");
        }

        //!!LoanTypeID 3 is home loan so Collaterate Cannot be soft which is 2 , and MOA for that is 1 even is hard title
        if (createLoanApplication.loanTypeId() == 3 && collateralTypesId.contains(2)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Home Loan cannot secure by Soft Title deed");
        }


        // End Validation
        LoanApplication loanApplication = loanApplicationMapper.fromCreateLoanApplicationRequest(createLoanApplication);
        loanApplication.setMaturityDate(LocalDate.now().plusMonths(loanApplication.getTenure()));
        loanApplication.setCollateralTypes(collateralTypes);
        loanApplication.setMoa(moaUtil.setMoaBasedOnCollateralTypes(createLoanApplication));
        loanApplication.setMaxLoanableAmount(createLoanApplication.totalCollateralValue().multiply(loanApplication.getMoa()));
        loanApplication.setDownPayment(moaUtil.setDownPaymentBasedOnLoanType(createLoanApplication));
        loanApplication.setCreatedAt(LocalDate.now());
        loanApplication.setMonthlyInstallment(calculateEMI.CalculateEMI(createLoanApplication, loanApplication));
        loanApplication.setLoanType(loanTypeRepository.findById(createLoanApplication.loanTypeId()).orElseThrow());
        if (loanApplication.getRequestAmount().compareTo(branchManagerApprovalLimit) <= 0) {
            loanApplication.setIsApprovedByBranchManager(false);
            loanApplication.setIsApprovedByHeadOfLoan(true);
        } else {
            loanApplication.setIsApprovedByBranchManager(false);
            loanApplication.setIsApprovedByHeadOfLoan(false);
        }
        loanApplication.setIsRejectedByHeadOfLoan(false);
        loanApplication.setIsRejectedByBM(false);
        loanApplication.setIsDrawDown(false);
        loanApplicationRepository.save(loanApplication);
        return loanApplicationMapper.toResponseLoanApplication(loanApplication);

    }

}
