package com.mbankingloan.mbankingloan.Feature.Auth.Controller;


import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.AuthService;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.AuthServiceCustomer;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request.*;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Response.JwtResponse;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.TwilioService;
import com.mbankingloan.mbankingloan.Util.ConvertPhoneNumber;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/api/v1/")
public class AuthController {

    private final AuthService authService;
    private final AuthServiceCustomer authServiceCustomer;
    private final TwilioService twilioService;
    private final ConvertPhoneNumber convertPhoneNumber;


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("staff/login")
    JwtResponse login(@Valid @RequestBody Login login) {
        return authService.login(login);
    }



    @ResponseStatus(HttpStatus.OK)
    @PostMapping("customer/login")
    JwtResponse loginCustomer(@Valid @RequestBody LoginCustomer loginCustomer) {
        return authServiceCustomer.loginCustomer(loginCustomer);
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("staff/refreshToken")
    JwtResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("customer/refreshToken")
    JwtResponse refreshTokenCustomer(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authServiceCustomer.refreshTokenCustomer(refreshTokenRequest);
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("email/staff/verify")
    void verify(@Valid @RequestBody StaffRegisterVerify staffRegisterVerify) {
        authService.registerVerify(staffRegisterVerify);

    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @PostMapping("email/customer/verify/")
//    void verifyCustomer(@Valid @RequestBody CustomerRegisterVerify customerRegisterVerify) {
//        authServiceCustomer.registerVerifyCustomer(customerRegisterVerify);
//
//    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("phoneNumber/customer/send")
    public String sendVerification(Principal principal) {
        String phoneNumber = principal.getName();
        String internationalPhoneNumber = convertPhoneNumber.convertToInternationalFormat(phoneNumber);
        return twilioService.sendVerificationCode(internationalPhoneNumber);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("phoneNumber/customer/verify")
    public Boolean verify(@RequestParam String code, Principal principal,String newPin, String newPassword) {
        String phoneNumber = principal.getName();
        String internationalPhoneNumber = convertPhoneNumber.convertToInternationalFormat(phoneNumber);
        return twilioService.verifyCode(internationalPhoneNumber, code, newPin,newPassword);
    }


}
