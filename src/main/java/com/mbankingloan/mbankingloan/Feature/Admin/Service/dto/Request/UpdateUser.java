package com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request;

import com.mbankingloan.mbankingloan.Domain.Branch;
import com.mbankingloan.mbankingloan.Domain.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;


public record UpdateUser(
         Integer id,
         String staffId,
         String firstName,
         String lastName,
         String email,
         String password,
         String phoneNumber,
         Integer BranchId,
         LocalDate birthDate,
         LocalDate hireDate,
         String provinceCity,
         String KhanDistrict,
         String village,
         String country,
         String position,
         List<Integer> rolesId
) {
}
