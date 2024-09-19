package com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response;

import com.mbankingloan.mbankingloan.Domain.Branch;

public record ResponseUser(
        String firstName,
        String lastName,
        String staffId,
        String email,
        String password,
        String phoneNumber,
        Branch branchCode,
        String position
) {
}
