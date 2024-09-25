package com.mbankingloan.mbankingloan.Util;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class GenerateVerificationCode {

    private static final SecureRandom secureRandom = new SecureRandom();

    public  String generateSecureCode() {
        int code = secureRandom.nextInt(900000) + 100000; // Generates a number between 100000 and 999999
        return String.valueOf(code);
    }


}
