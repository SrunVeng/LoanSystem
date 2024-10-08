package com.mbankingloan.mbankingloan.Feature.Admin.Controller;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.UserRequest;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.DeleteUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RecoverUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RegisterUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.UpdateUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseUser;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/v1/user")
public class UserControllerAdmin {

    private final UserRequest userRequest;

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_LOAN-OFFICER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllUsers")
    List<ResponseUser> getAllUsers() {
        return userRequest.getAllUsers();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_LOAN-OFFICER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getUser/{id}")
    ResponseUser getUserById(@PathVariable int id) {
        return userRequest.getUserById(id);
    }




    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    ResponseUser registerUser(@Valid @RequestBody RegisterUser registerUser) throws MessagingException {
        return userRequest.registerUser(registerUser);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/deleteUser")
    ResponseUser deleteUser(@Valid @RequestBody DeleteUser deleteUser) {
        return userRequest.deleteUser(deleteUser);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/recoverUser")
    ResponseUser recoverUser(@Valid @RequestBody RecoverUser recoverUser) {
        return userRequest.recoverUser(recoverUser);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/PermanentlyDelete")
    void PermanentlyDeleteUser(@Valid @RequestBody DeleteUser deleteUser) {
         userRequest.permanentlyDeleteUser(deleteUser);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/updateUser")
    ResponseUser updateUser(@Valid @RequestBody UpdateUser updateUser) {
        return userRequest.updateUser(updateUser);
    }




}
