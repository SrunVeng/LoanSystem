package com.mbankingloan.mbankingloan.Feature.Admin.Service;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.DeleteUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RegisterUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.UpdateUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;


import java.util.List;

public interface UserRequest {

       ResponseUser registerUser(RegisterUser registerUser);


       List<ResponseUser> getAllUsers();



       ResponseUser deleteUserByid(DeleteUser deleteUser);

       void permanentlyDeleteUserById(DeleteUser deleteUser);


       ResponseUser updateUserByid(UpdateUser updateUser);
}
