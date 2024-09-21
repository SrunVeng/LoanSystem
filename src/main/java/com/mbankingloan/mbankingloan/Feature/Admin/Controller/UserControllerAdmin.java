package com.mbankingloan.mbankingloan.Feature.Admin.Controller;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.UserRequest;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.DeleteUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RecoverUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RegisterUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.UpdateUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserControllerAdmin {

    private final UserRequest userRequest;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllUsers")
    List<ResponseUser> getAllUsers() {
        return userRequest.getAllUsers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    ResponseUser registerUser(@Valid @RequestBody RegisterUser registerUser) {
        return userRequest.registerUser(registerUser);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/deleteUserById")
    ResponseUser deleteUserById(@Valid @RequestBody DeleteUser deleteUser) {
        return userRequest.deleteUserByid(deleteUser);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/recoverUserById")
    ResponseUser recoverUserById(@Valid @RequestBody RecoverUser recoverUser) {
        return userRequest.recoverUserByid(recoverUser);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/PermanentlyDeleteById")
    void PermanentlyDeleteUserById(@Valid @RequestBody DeleteUser deleteUser) {
         userRequest.permanentlyDeleteUserById(deleteUser);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/updateUserById")
    ResponseUser updateUserById(@Valid @RequestBody UpdateUser updateUser) {
        return userRequest.updateUserByid(updateUser);
    }




}
