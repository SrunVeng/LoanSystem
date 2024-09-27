package com.mbankingloan.mbankingloan.Feature.Admin.Service;

import com.mbankingloan.mbankingloan.Domain.Branch;
import com.mbankingloan.mbankingloan.Domain.EmailVerification;
import com.mbankingloan.mbankingloan.Domain.Role;
import com.mbankingloan.mbankingloan.Domain.User;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.BranchRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.RoleRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.UserRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.DeleteUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RecoverUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.RegisterUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.UpdateUser;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Response.ResponseUser;
import com.mbankingloan.mbankingloan.Feature.Auth.Repository.EmailRepository;
import com.mbankingloan.mbankingloan.Mapper.UserMapper;
import com.mbankingloan.mbankingloan.Util.GenerateStaffID;
import com.mbankingloan.mbankingloan.Util.GenerateVerificationCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRequestImpl implements UserRequest {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final GenerateStaffID generateStaffID;
    private final GenerateVerificationCode generateVerificationCode;
    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;
    @Value("${spring.mail.username}")
    private String adminMail;


    @Override
    @Transactional
    public ResponseUser registerUser(RegisterUser registerUser) throws MessagingException {

        // Validate Email and Phone Number is Duplicate
        if (userRepository.existsByEmail(registerUser.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        if (userRepository.existsByPhoneNumber(registerUser.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "PhoneNumber already exists");
        }


        List<Role> checkRoles = roleRepository.findAllById(registerUser.rolesId());
        if (checkRoles.size() != registerUser.rolesId().size()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Roles do not exists");
        }

        List<Role> roles = new ArrayList<>();
        roles.addAll(checkRoles);
        User newUser = userMapper.fromUserRegister(registerUser);
        Branch branch = branchRepository.findById(registerUser.branchCodeId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch Not Found"));
        newUser.setRoles(roles);
        newUser.setBranchCode(branch);
        newUser.setCreatedAt(LocalDate.now());
        newUser.setIsVerified(false);
        newUser.setIsBlock(true);
        newUser.setIsDeleted(false);
        newUser.setRoles(roles);
        newUser.setIsAccountNonLocked(true);
        newUser.setIsCredentialsNonExpired(true);
        newUser.setIsAccountNonExpired(true);
        newUser.setStaffId(generateStaffID.generateStaffId());
        newUser.setPassword("Mbanking@#123");
        userRepository.save(newUser);

        //1.Prepare Email Code In Repo Email
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setUser(newUser);
        emailVerification.setVerificationCode(generateVerificationCode.generateSecureCode());
        emailVerification.setExpiryTime(LocalTime.now().plusHours(1));
        emailRepository.save(emailVerification);
        //
        //2.Send Mail
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(newUser.getEmail());
        helper.setFrom(adminMail);
        helper.setText(emailVerification.getVerificationCode());
        mailSender.send(mimeMessage);


        return userMapper.toUserResponse(newUser);
    }

    @Override
    public List<ResponseUser> getAllUsers() {
        return userMapper.toUserAllResponses(userRepository.findAll());
    }

    @Override
    @Transactional
    public ResponseUser deleteUser(DeleteUser deleteUser) {

        if (!userRepository.existsById(deleteUser.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "UserID Not Found");
        }

        if (!userRepository.existsByFirstName(deleteUser.firstName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User FirstName Not Found");
        }
        if (!userRepository.existsByLastName(deleteUser.lastName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User LastName Not Found");
        }

        // check 3

        User user = userRepository.findByIdAndFirstNameAndLastName(deleteUser.id(), deleteUser.firstName(), deleteUser.lastName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "ID firstName and lastName of User does not match"));

        user.setIsDeleted(true);

        return userMapper.toUserResponse(userRepository.findById(deleteUser.id()).orElseThrow());

    }

    @Override
    @Transactional
    public void permanentlyDeleteUser(DeleteUser deleteUser) {

        if (!userRepository.existsById(deleteUser.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "UserID Not Found");
        }

        if (!userRepository.existsByFirstName(deleteUser.firstName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User FirstName Not Found");
        }
        if (!userRepository.existsByLastName(deleteUser.lastName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User LastName Not Found");
        }
        userRepository.deleteByIdAndFirstNameAndLastName(deleteUser.id(), deleteUser.firstName(), deleteUser.lastName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "ID firstName and lastName of User does not match"));


    }

    @Override
    @Transactional
    public ResponseUser updateUser(UpdateUser updateUser) {
        if (!userRepository.existsById(updateUser.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "UserID Not Found");
        }
        User user = userRepository.findById(updateUser.id()).orElseThrow();
// Update branch if present
        if (updateUser.BranchId() != null) {
            Branch newBranch = branchRepository.findById(updateUser.BranchId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch Not Found"));
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

    @Override
    @Transactional
    public ResponseUser recoverUser(RecoverUser recoverUser) {

        if (!userRepository.existsById(recoverUser.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "UserID Not Found");
        }

        if (!userRepository.existsByFirstName(recoverUser.firstName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User FirstName Not Found");
        }
        if (!userRepository.existsByLastName(recoverUser.lastName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User LastName Not Found");
        }


        User user = userRepository.findByIdAndFirstNameAndLastName(recoverUser.id(), recoverUser.firstName(), recoverUser.lastName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "ID firstName and lastName of User does not match"));

        user.setIsDeleted(false);

        return userMapper.toUserResponse(userRepository.findById(recoverUser.id()).orElseThrow());

    }

    @Override
    public ResponseUser getUserById(int id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User Not Found");
        }

        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow());
    }
}
