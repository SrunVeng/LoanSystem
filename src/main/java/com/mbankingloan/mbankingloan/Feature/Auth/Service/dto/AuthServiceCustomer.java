package com.mbankingloan.mbankingloan.Feature.Auth.Service.dto;


import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request.*;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Response.JwtResponse;
import jakarta.validation.Valid;

public interface AuthServiceCustomer {

    JwtResponse loginCustomer(@Valid LoginCustomer loginCustomer);

    JwtResponse refreshTokenCustomer(@Valid RefreshTokenRequest refreshTokenRequest);

//    void registerVerifyCustomer(@Valid CustomerRegisterVerify customerRegisterVerify);


}
