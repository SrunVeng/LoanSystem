package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;


public record CreateCustomerCiF(


        @NotBlank(message = "FirstName is required")
        String firstName,
        @NotBlank(message = "LastName is required")
        String lastName,
        @NotNull(message = " BirthDate is required")
        LocalDate dateOfBirth,

        @NotBlank(message = " Gender is required")
        String gender,

        @NotBlank(message = "Email is required")
        @Email
        String email,

        @NotBlank(message = "Position is required")
        String position,

        @NotBlank(message = "MainSourceOfIncome is required")
        String mainSourceOfIncome,

        @NotNull(message = "Monthly is required")
        BigDecimal monthlyIncome,

        @NotBlank(message = "NID is required")
        String nationalCardId,

        String cityOrProvince,
        String khanOrDistrict,
        String sangKatOrCommune,
        String village,
        String street,

        @NotBlank(message = "Phone number is required")
        String phoneNumber


        //Optional Customer can set by theirOwn
//        @Email
//        String email,
//        String profileImageUrl,
//        String companyName,
//        String employeeType

) {
}
