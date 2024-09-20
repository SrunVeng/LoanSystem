package com.mbankingloan.mbankingloan.Feature.Admin.Service;

import com.mbankingloan.mbankingloan.Domain.Branch;
import com.mbankingloan.mbankingloan.Domain.CollateralType;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.CollateralTypeRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.CreateCollateralType;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseCollateralType;
import com.mbankingloan.mbankingloan.Mapper.CollateralMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        collateralType.setMoa(collateralType.getMoa().divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_DOWN));

        collateralTypeRepository.save(collateralType);

        return collateralMapper.toCollateralTypeResponse(collateralType);

    }

    @Override
    public List<ResponseCollateralType> getAllCollateralTypes() {
        return collateralMapper.toAllCollateralTypesResponse(collateralTypeRepository.findAll());
    }
}
