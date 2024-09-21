package com.mbankingloan.mbankingloan.Util;

import com.mbankingloan.mbankingloan.Feature.Admin.Repository.UserRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class GenerateCustomerCIFNumber {

    private final CustomerRepository customerRepository;


    public String generateCustomerCIFNumber() {
        String customerCIFNumber;
        Random random = new Random();

        do {
            customerCIFNumber = String.valueOf(10000000000L + (long)(random.nextInt(900000000))); // Generate a 6-digit number
        } while (customerRepository.existsByCustomerCIFNumber(customerCIFNumber));

        return customerCIFNumber;
    }

}
