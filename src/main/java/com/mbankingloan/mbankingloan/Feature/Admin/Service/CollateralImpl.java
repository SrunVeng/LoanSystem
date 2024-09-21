package com.mbankingloan.mbankingloan.Feature.Admin.Service;

import com.mbankingloan.mbankingloan.Domain.CollateralType;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.CollateralTypeRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateCollateralType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.DeleteCollateralType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RecoverCollateralType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseCollateralType;
import com.mbankingloan.mbankingloan.Mapper.CollateralMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CollateralImpl implements CollateralRequest {

    private final CollateralTypeRepository collateralTypeRepository;
    private final CollateralMapper collateralMapper;


    @Override
    public ResponseCollateralType createCollateralType(CreateCollateralType createCollateralType) {

        CollateralType collateralType = collateralMapper.fromCollateralTypeRequest(createCollateralType);
        collateralType.setMoa(collateralType.getMoa().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_DOWN));

        collateralTypeRepository.save(collateralType);

        return collateralMapper.toCollateralTypeResponse(collateralType);

    }

    @Override
    public List<ResponseCollateralType> getAllCollateralTypes() {
        return collateralMapper.toAllCollateralTypesResponse(collateralTypeRepository.findAll());
    }


    @Override
    @Transactional
    public ResponseCollateralType deleteCollateralType(DeleteCollateralType deleteCollateral) {

        if (!collateralTypeRepository.existsById(deleteCollateral.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ID of CollatateralType do not exist");
        }

        if (!collateralTypeRepository.existsByTitle(deleteCollateral.title())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Title of CollatateralType do not exist");
        }
        CollateralType collateralType = collateralTypeRepository.findByIdAndTitle(deleteCollateral.id(), deleteCollateral.title()).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Title and ID of CollatateralType do not match"));


        collateralType.setIsDeleted(true);

        return collateralMapper.toCollateralTypeResponse(collateralType);

    }

    @Override
    @Transactional
    public ResponseCollateralType recoverCollateralType(RecoverCollateralType recoverCollateral) {

        if (!collateralTypeRepository.existsById(recoverCollateral.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ID of CollatateralType do not exist");
        }

        CollateralType collateralType = collateralTypeRepository.findById(recoverCollateral.id()).orElseThrow();
        collateralType.setIsDeleted(false);

        return collateralMapper.toCollateralTypeResponse(collateralType);
    }
}
