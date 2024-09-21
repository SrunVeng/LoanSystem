package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response;


import java.math.BigDecimal;
import java.time.LocalDate;


public record ResponseCustomerDetails(

        String customerCIFNumber,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String nationalCardId,
        String gender,
        Integer BranchId,
        String profileImageUrl,
        LocalDate dateOfBirth,
        String cityOrProvince,
        String companyName,
        String employeeType,
        String khanOrDistrict,
        String sangKatOrCommune,
        String mainSourceOfIncome,
        BigDecimal monthlyIncome,
        String village,
        String street,
        String position,
        Boolean isDeleted

        //Generate By System
) {
}
