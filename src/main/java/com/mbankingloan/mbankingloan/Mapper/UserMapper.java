package com.mbankingloan.mbankingloan.Mapper;


import com.mbankingloan.mbankingloan.Domain.User;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RegisterUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.UpdateUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseUser;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromUserRegister(RegisterUser registerUser);
    ResponseUser toUserResponse(User user);

    List<ResponseUser> toUserAllResponses(List<User> users);

    //Partially update
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdateUserRequestPartially(@MappingTarget User user, UpdateUser updateUser);


}
