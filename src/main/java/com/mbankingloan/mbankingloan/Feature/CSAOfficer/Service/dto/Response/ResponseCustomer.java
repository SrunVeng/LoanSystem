package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response;


import java.math.BigDecimal;
import java.time.LocalDate;


public record ResponseCustomer(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String gender,
        String position,
        String mainSourceOfIncome,
        BigDecimal monthlyIncome,
        String nationalCardId,
        String cityOrProvince,
        String khanOrDistrict,
        String sangKatOrCommune,
        String village,
        String street,
        String phoneNumber

        //Generate By System
) {
}
