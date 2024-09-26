package com.mbankingloan.mbankingloan.Feature.Auth.Controller;


import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.AuthService;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request.*;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Response.JwtResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/api/v1/")
public class AuthController {

    private final AuthService authService;


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    JwtResponse login(@Valid @RequestBody Login login) {
        return authService.login(login);
    }



    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/refreshToken")
    JwtResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/verify/email")
    void verify(@Valid @RequestBody StaffRegisterVerify staffRegisterVerify) {
        authService.registerVerify(staffRegisterVerify);

    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("customer/verify/email")
    void verifyCustomer(@Valid @RequestBody CustomerRegisterVerify customerRegisterVerify) {
        authService.registerVerifyCustomer(customerRegisterVerify);

    }





}
