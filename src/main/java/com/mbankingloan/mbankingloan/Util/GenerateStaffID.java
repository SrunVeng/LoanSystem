package com.mbankingloan.mbankingloan.Util;

import com.mbankingloan.mbankingloan.Feature.Admin.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class GenerateStaffID {

    private final UserRepository userRepository;


    public String generateStaffId() {
        String staffId;
        Random random = new Random();

        // Ensure uniqueness by checking the database for existing staff IDs
        do {
            staffId = String.valueOf(100000 + random.nextInt(900000)); // Generate a 6-digit number
        } while (userRepository.existsByStaffId(staffId));

        return staffId; // Return the unique staff ID
    }

}
