package com.mbankingloan.mbankingloan.Feature.Auth.Service.dto;

public interface TwilioService {

     String sendVerificationCode(String phoneNumber);

     Boolean verifyCode(String phoneNumber, String code, String newPin,String newPassword);

}
