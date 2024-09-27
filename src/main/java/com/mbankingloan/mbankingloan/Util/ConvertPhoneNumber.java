package com.mbankingloan.mbankingloan.Util;


import org.springframework.stereotype.Component;

@Component
public class ConvertPhoneNumber {


    public String convertToInternationalFormat(String phoneNumber) {
        if (phoneNumber.startsWith("0")) {
            return "+855" + phoneNumber.substring(1); // Replace '0' with '+855'
        }
        return phoneNumber; // Return as is if already in international format
    }
    // Return as is if already in international format

    public String convertToLocalFormat(String internationalPhoneNumber) {
        if (internationalPhoneNumber.startsWith("+855")) {
            return "0" + internationalPhoneNumber.substring(4); // Replace '+855' with '0'
        }
        return internationalPhoneNumber; // Return as is if not in international format
    }

}
