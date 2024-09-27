package com.mbankingloan.mbankingloan.Feature.Auth.Service.dto;

import com.mbankingloan.mbankingloan.Config.Twilio.TwilioConfig;
import com.mbankingloan.mbankingloan.Domain.Customer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.CustomerRepository;
import com.mbankingloan.mbankingloan.Util.ConvertPhoneNumber;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TwilioServiceImpl implements TwilioService {

    private final TwilioConfig twilioConfig;
    private final CustomerRepository customerRepository;
    private final ConvertPhoneNumber convertPhoneNumber;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TwilioServiceImpl(TwilioConfig twilioConfig, CustomerRepository customerRepository, ConvertPhoneNumber convertPhoneNumber, PasswordEncoder passwordEncoder) {
        this.twilioConfig = twilioConfig;
        this.customerRepository = customerRepository;
        this.convertPhoneNumber = convertPhoneNumber;
        this.passwordEncoder = passwordEncoder;
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    @Override
    public String sendVerificationCode(String phoneNumber) {
        Verification verification = Verification.creator(
                twilioConfig.getVerifyServiceSid(),
                phoneNumber,
                "sms"
        ).create();

        return verification.getSid(); // Return the SID of the verification
    }

    @Override
    public Boolean verifyCode(String phoneNumber, String code, String newPin, String newPassword) {
        VerificationCheck verificationCheck = VerificationCheck.creator(
                twilioConfig.getVerifyServiceSid(),
                code // Pass the code directly here
        ).setTo(phoneNumber).create();

        String LocalPhoneNumberFormat = convertPhoneNumber.convertToLocalFormat(phoneNumber);
        // Check if the status is approved
        if ("approved".equals(verificationCheck.getStatus())) {
            // Update customer's verification status
            Customer customer = customerRepository.findByphoneNumber(LocalPhoneNumberFormat);
            if (customer != null) {
                customer.setIsVerified(true);
                customer.setIsBlock(false);
                customer.setPassword(passwordEncoder.encode(newPassword));
                customer.setPin(passwordEncoder.encode(newPin));
                customerRepository.save(customer); // Save the updated customer
            }
            return true;
        }
        return false;
    }
}
