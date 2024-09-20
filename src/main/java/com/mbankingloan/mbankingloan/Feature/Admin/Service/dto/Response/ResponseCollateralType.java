package com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response;


import java.math.BigDecimal;

public record ResponseCollateralType(
        Integer id,
        String title,

        BigDecimal moa
){

}

