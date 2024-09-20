package com.mbankingloan.mbankingloan.Feature.Admin.Service;

import com.mbankingloan.mbankingloan.Domain.Branch;
import com.mbankingloan.mbankingloan.Domain.Role;
import com.mbankingloan.mbankingloan.Domain.User;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.BranchRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.RoleRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.UserRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.DeleteUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RegisterUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.UpdateUser;
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
public class UserRequestImpl implements UserRequest {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final GenerateStaffID generateStaffID;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ResponseUser registerUser(RegisterUser registerUser) {

        // Validate Email and Phone Number is Duplicate
        if (userRepository.existsByemail(registerUser.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        if (userRepository.existsByphoneNumber(registerUser.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "PhoneNumber already exists");
        }


        List<Role> roles = roleRepository.findAllById(registerUser.rolesId());
        if (roles.size() != registerUser.rolesId().size()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Roles do not exists");
        }

        User newUser = userMapper.fromUserRegister(registerUser);
        Branch branch = branchRepository.findById(registerUser.branchCodeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch Not Found"));
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

    @Override
    public List<ResponseUser> getAllUsers() {
        return userMapper.toUserAllResponses(userRepository.findAll());
    }

    @Override
    public ResponseUser deleteUserByid(DeleteUser deleteUser) {

        if (!userRepository.existsById(deleteUser.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "UserID Not Found");
        }

        if (!userRepository.existsByfirstName(deleteUser.firstName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User FirstName Not Found");
        }
        if (!userRepository.existsBylastName(deleteUser.lastName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User LastName Not Found");
        }

        User user = userRepository.findById(deleteUser.id()).orElseThrow();
        user.setIsDeleted(true);

        return userMapper.toUserResponse(userRepository.findById(deleteUser.id()).orElseThrow());

    }

    @Override
    public void permanentlyDeleteUserById(DeleteUser deleteUser) {

        if (!userRepository.existsById(deleteUser.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "UserID Not Found");
        }

        if (!userRepository.existsByfirstName(deleteUser.firstName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User FirstName Not Found");
        }
        if (!userRepository.existsBylastName(deleteUser.lastName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User LastName Not Found");
        }

        userRepository.deleteById(deleteUser.id());

    }

    @Override
    public ResponseUser updateUserByid(UpdateUser updateUser) {
        if (!userRepository.existsById(updateUser.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "UserID Not Found");
        }
        User user = userRepository.findById(updateUser.id()).orElseThrow();
// Update branch if present
        if (updateUser.BranchId() != null) {
            Branch newBranch = branchRepository.findById(updateUser.BranchId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch Not Found"));
            user.setBranchCode(newBranch);
        }

// Update roles if present
        if (updateUser.rolesId() != null) {
            List<Role> roles = roleRepository.findAllById(updateUser.rolesId());
            if (roles.size() != updateUser.rolesId().size()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Roles do not exist");
            }
            user.setRoles(roles);
        }

// Apply remaining updates
        userMapper.fromUpdateUserRequestPartially(user, updateUser);
        userRepository.save(user);

        return userMapper.toUserResponse(user);

    }
}
