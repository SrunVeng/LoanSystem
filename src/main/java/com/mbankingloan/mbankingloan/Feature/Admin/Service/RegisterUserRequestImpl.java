package com.mbankingloan.mbankingloan.Feature.Admin.Service;

import com.mbankingloan.mbankingloan.Domain.Branch;
import com.mbankingloan.mbankingloan.Domain.Role;
import com.mbankingloan.mbankingloan.Domain.User;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.BranchRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.RoleRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.UserRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RegisterUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseUser;
import com.mbankingloan.mbankingloan.Mapper.UserMapper;
import com.mbankingloan.mbankingloan.Util.GenerateStaffID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterUserRequestImpl implements RegisterUserRequest {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final GenerateStaffID generateStaffID;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ResponseUser registerUser(RegisterUser registerUser) {

        // Validate Email and Phone Number is Duplicate
        if(userRepository.existsByemail(registerUser.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        if(userRepository.existsByphoneNumber(registerUser.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "PhoneNumber already exists");
        }



        User newUser = userMapper.fromUserRegister(registerUser);
        Branch branch = branchRepository.findById(registerUser.branchCodeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch Not Found"));
        List<Role> roles = roleRepository.findAllById(registerUser.rolesId());


        newUser.setRoles(roles);
        newUser.setBranchCode(branch);
        newUser.setCreatedAt(LocalDate.now());
        newUser.setIsVerified(false);
        newUser.setIsBlock(false);
        newUser.setIsDeleted(false);
        newUser.setIsAccountNonLocked(true);
        newUser.setIsCredentialsNonExpired(true);
        newUser.setIsAccountNonExpired(true);
        newUser.setStaffId(generateStaffID.generateStaffId());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);

        return userMapper.toUserResponse(newUser);
    }
}
