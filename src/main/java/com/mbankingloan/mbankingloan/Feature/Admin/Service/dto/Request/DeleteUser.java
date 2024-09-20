package com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;


public record DeleteUser(

        @NotNull(message = "ID is required")
        Integer id,
        @NotBlank(message = "FirstName is required")
        String firstName,
        @NotBlank(message = "LastName is required")
        String lastName


) {
}
