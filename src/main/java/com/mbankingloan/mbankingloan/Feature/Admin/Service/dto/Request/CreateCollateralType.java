package com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request;

import java.math.BigDecimal;


public record CreateCollateralType(

        String title,

        BigDecimal moa

) {
}
