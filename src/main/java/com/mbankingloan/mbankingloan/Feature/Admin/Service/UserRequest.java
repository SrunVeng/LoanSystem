package com.mbankingloan.mbankingloan.Feature.Admin.Service;


import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.DeleteUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RecoverUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RegisterUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.UpdateUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseUser;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;


import java.util.List;

public interface UserRequest {

       ResponseUser registerUser(RegisterUser registerUser) throws MessagingException;

       List<ResponseUser> getAllUsers();

       ResponseUser deleteUser(DeleteUser deleteUser);

       void permanentlyDeleteUser(DeleteUser deleteUser);

       ResponseUser updateUser(UpdateUser updateUser);

       ResponseUser recoverUser( RecoverUser recoverUser);


     ResponseUser getUserById(int id);
}
