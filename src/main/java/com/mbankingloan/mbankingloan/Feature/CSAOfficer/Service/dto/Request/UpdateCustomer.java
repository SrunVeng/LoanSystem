package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public record UpdateCustomer(

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
         String position


) {
}
