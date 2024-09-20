package com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;


public record RegisterUser(
        @NotBlank(message = "FirstName is required")
        String firstName,
        @NotBlank(message = "LastName is required")
        String lastName,
        @NotNull(message = " BirthDate is required")
        LocalDate birthDate,
        @NotNull(message = "HireDate is required")
        LocalDate hireDate,
        @NotBlank(message = "Position is required")
        String position,
        @Email
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Phone number is required")
        String phoneNumber,
        @NotBlank(message = "Password is required")
        String password,
        String provinceCity,
        String KhanDistrict,
        String village,
        String country,

        @NotNull(message = "BranchCodeId is required")
        Integer branchCodeId,

        @NotNull(message = "RoleId is required")
        List<Integer> rolesId
) {
}
