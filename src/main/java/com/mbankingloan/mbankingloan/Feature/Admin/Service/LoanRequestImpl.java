package com.mbankingloan.mbankingloan.Feature.Admin.Service;

import com.mbankingloan.mbankingloan.Domain.CollateralType;
import com.mbankingloan.mbankingloan.Domain.Loan;
import com.mbankingloan.mbankingloan.Domain.LoanType;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.CollateralTypeRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanApplicationRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanTypeRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateLoanType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.DeleteLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoanAccountType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoanType;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.LoanAccountRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.LoanAccountTypeRepository;
import com.mbankingloan.mbankingloan.Mapper.LoanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LoanRequestImpl implements LoanRequest {

    private final LoanTypeRepository loanTypeRepository;
    private final LoanRepository loanRepository;
    private final LoanAccountTypeRepository loanAccountTypeRepository;
    private final CollateralTypeRepository collateralTypeRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanMapper loanMapper;


    // Create Loan Type

    @Override
    public ResponseLoanType createLoanType(CreateLoanType createLoanType) {
        if (loanTypeRepository.existsByName(createLoanType.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "LoanType already exists");
        }
        LoanType loanType = loanMapper.fromLoanTypeRequest(createLoanType);
        loanTypeRepository.save(loanType);
        return loanMapper.toResponseLoanType(loanType);
    }
    // End

    //Create Loan

    @Override
    public ResponseLoan createLoan(CreateLoan createLoan) {


        if (!loanTypeRepository.existsById(createLoan.loanTypeId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "LoanType Does not exists");
        }

        if (createLoan.interestRate().compareTo(BigDecimal.valueOf(18)) > 0) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Interest Must be not exceed 18%");
        }
        if (createLoan.moa().compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "MOA Must be not exceed 100%");
        }

        List<CollateralType> collateralTypes = collateralTypeRepository.findAllById(createLoan.collateralTypesId());
        if (collateralTypes.size() != createLoan.collateralTypesId().size()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CollateralTypes do not exists");
        }


        LoanType loanType = loanTypeRepository.findById(createLoan.loanTypeId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "LoanType Not Found"));
        Loan newLoan = loanMapper.fromLoanRequest(createLoan);
        newLoan.setLoantype(loanType);
        newLoan.setCollateralTypes(collateralTypes);
        newLoan.setIsDeleted(false);
        loanRepository.save(newLoan);

        return loanMapper.toLoanResponse(newLoan);

    }

    @Override
    public List<ResponseLoan> getAllLoans() {
        return loanMapper.toAllLoanResponse(loanRepository.findAll());
    }

    @Override
    public List<ResponseLoanType> getAllLoanTypes() {
        return loanMapper.toAllLoanTypeResponse(loanTypeRepository.findAll());
    }

    @Override
    @Transactional
    public ResponseLoan deleteLoan(DeleteLoan deleteLoan) {

        if (loanApplicationRepository.existsByLoan_Id(deleteLoan.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan is Under LoanApplication Cannot be Deleted");

        }

        if (!loanRepository.existsById(deleteLoan.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan ID not found");
        }
        if (!loanRepository.existsByName(deleteLoan.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name not found");
        }

        Loan loan = loanRepository.findByIdAndName(deleteLoan.id(), deleteLoan.name()).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Name and ID of Loan do not match"));

        loan.setIsDeleted(true);

        return loanMapper.toLoanResponse(loan);

    }

    @Override
    @Transactional
    public ResponseLoanType deleteLoanType(DeleteLoan deleteLoan) {

        if (!loanTypeRepository.existsById(deleteLoan.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "LoanType ID Does not exists");
        }

        if (!loanTypeRepository.existsByName(deleteLoan.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name not found");
        }


        LoanType loanType = loanTypeRepository.findByIdAndName(deleteLoan.id(), deleteLoan.name()).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Name and ID of LoanType do not match"));

        loanType.setIsDeleted(true);

        return loanMapper.toResponseLoanType(loanType);

    }

    @Override
    @Transactional
    public ResponseLoan recoverLoanById(int id) {

        Loan loan = loanRepository.findById(id).orElseThrow();
        loan.setIsDeleted(false);

        return loanMapper.toLoanResponse(loan);
    }

    @Override
    @Transactional
    public ResponseLoanType recoverLoanTypeById(int id) {

        LoanType loanType = loanTypeRepository.findById(id).orElseThrow();
        loanType.setIsDeleted(false);

        return loanMapper.toResponseLoanType(loanType);
    }


    @Override
    public ResponseLoan getLoanById(int id) {

        if(!loanRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found");
        }

        return loanMapper.toLoanResponse(loanRepository.findById(id).orElseThrow());
    }

    @Override
    public ResponseLoanType getLoanTypeById(int id) {
        if(!loanTypeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LoanType not found");
        }

        return loanMapper.toResponseLoanType(loanTypeRepository.findById(id).orElseThrow());
    }

    @Override
    public List<ResponseLoanAccountType> getAllLoanAccountTypes() {
        return loanMapper.toResponseLoanAccountTypeList(loanAccountTypeRepository.findAll());
    }

    // End


}
