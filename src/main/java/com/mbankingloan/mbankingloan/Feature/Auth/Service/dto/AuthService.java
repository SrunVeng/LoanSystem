package com.mbankingloan.mbankingloan.Feature.Auth.Service.dto;



import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request.*;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Response.JwtResponse;
import jakarta.validation.Valid;

public interface AuthService {

    JwtResponse login(@Valid Login login);
    

    JwtResponse refreshToken(@Valid RefreshTokenRequest refreshTokenRequest);

    void registerVerify(@Valid StaffRegisterVerify staffRegisterVerify);



    void registerVerifyCustomer(@Valid CustomerRegisterVerify customerRegisterVerify);
}
