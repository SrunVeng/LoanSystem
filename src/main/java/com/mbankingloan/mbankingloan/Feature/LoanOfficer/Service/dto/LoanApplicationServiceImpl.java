package com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto;


import com.mbankingloan.mbankingloan.Domain.CollateralType;
import com.mbankingloan.mbankingloan.Domain.Customer;
import com.mbankingloan.mbankingloan.Domain.LoanApplication;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.CollateralTypeRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanApplicationRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.UserRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.CustomerRepository;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Request.CreateLoanApplication;
import com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto.Response.ResponseLoanApplication;
import com.mbankingloan.mbankingloan.Mapper.LoanApplicationMapper;
import com.mbankingloan.mbankingloan.Util.LoanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanRepository loanRepository;
    private final CollateralTypeRepository collateralTypeRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final LoanUtil loanUtil;
    private final LoanApplicationMapper loanApplicationMapper;

    @Override
    public ResponseLoanApplication createLoanApplication(CreateLoanApplication createLoanApplication, String StaffId) {

        //Declare
        List<Integer> collateralTypesId = createLoanApplication.collateralID();
        List<CollateralType> collateralTypes = collateralTypeRepository.findAllById(collateralTypesId);
        List<String> cifNumbers = createLoanApplication.customerCifNumber();
        List<Customer> existingCustomers = customerRepository.findAllByCustomerCIFNumberIn(cifNumbers);
        List<String> existingCifNumbers = existingCustomers.stream().map(Customer::getCustomerCIFNumber) // Adjust this to your actual method for getting the CIF number
                .toList();
        boolean isOutOfRangeCollateralTypeId = collateralTypesId.stream().anyMatch(type -> type < 1 || type > collateralTypeRepository.count());
        boolean anyNonExistent = cifNumbers.stream().anyMatch(cif -> !existingCifNumbers.contains(cif));

        //End Declare

        //Validation
        if (anyNonExistent) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "One or more Customer CIF numbers do not exist.");
        }

        if (isOutOfRangeCollateralTypeId) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CollateralTypeID must be in the range of 1 to " + collateralTypeRepository.count());
        }
        if (collateralTypesId.size() > collateralTypeRepository.count()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You cannot select more than " + collateralTypeRepository.count() + " collateral typesID.");
        }
        // End Validation
        LoanApplication loanApplication = loanApplicationMapper.fromCreateLoanApplicationRequest(createLoanApplication);
        loanApplication.setMaturityDate(LocalDate.now().plusMonths(loanApplication.getTenure()));
        loanApplication.setCollateralTypes(collateralTypes);
        loanApplication.setLoan(loanUtil.getLoanByAmount(createLoanApplication.requestAmount()));
        loanApplication.setMoa(loanUtil.setMoaBasedOnCollateralTypes(createLoanApplication));
        loanApplication.setMaxLoanableAmount(createLoanApplication.totalCollateralValue().multiply(loanApplication.getMoa()));
        loanApplication.setMonthlyInstallment(loanUtil.CalculateEMI(createLoanApplication, loanApplication));
        loanApplication.setCreatedAt(LocalDate.now());
        loanApplication.setUser(userRepository.findByStaffId(StaffId));
        if (!loanApplication.getLoan().equals(loanRepository.findById(7).orElse(null))) {
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
