package com.mbankingloan.mbankingloan.Feature.Admin.Controller;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.RegisterUserRequest;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RegisterUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserControllerAdmin {

    private final RegisterUserRequest registerUserRequest;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    ResponseUser registerUser(@Valid @RequestBody RegisterUser registerUser) {
        return registerUserRequest.registerUser(registerUser);
    }


}
