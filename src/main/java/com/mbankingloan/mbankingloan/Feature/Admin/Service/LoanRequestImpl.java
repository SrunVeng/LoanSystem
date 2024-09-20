package com.mbankingloan.mbankingloan.Feature.Admin.Service;

import com.mbankingloan.mbankingloan.Domain.CollateralType;
import com.mbankingloan.mbankingloan.Domain.Loan;
import com.mbankingloan.mbankingloan.Domain.LoanType;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.CollateralTypeRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanTypeRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateLoanType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoan;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseLoanType;
import com.mbankingloan.mbankingloan.Mapper.LoanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LoanRequestImpl implements LoanRequest {

    private final LoanTypeRepository loanTypeRepository;
    private final LoanRepository loanRepository;
    private final CollateralTypeRepository collateralTypeRepository;
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


        if(!loanTypeRepository.existsById(createLoan.loanTypeId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "LoanType Does not exists");
        }

        if(createLoan.interestRate().compareTo(BigDecimal.valueOf(18)) > 0){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Interest Must be not exceed 18%");
        }
        if(createLoan.moa().compareTo(BigDecimal.valueOf(100)) > 0){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "MOA Must be not exceed 100%");
        }

        List<CollateralType> collateralTypes = collateralTypeRepository.findAllById(createLoan.collateralTypesId());
        if(collateralTypes.size() != createLoan.collateralTypesId().size()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CollateralTypes do not exists");
        }


        LoanType loanType = loanTypeRepository.findById(createLoan.loanTypeId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "LoanType Not Found"));
        Loan newLoan = loanMapper.fromLoanRequest(createLoan);
        newLoan.setLoantype(loanType);
        newLoan.setCollateralTypes(collateralTypes);
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


    // End


}
