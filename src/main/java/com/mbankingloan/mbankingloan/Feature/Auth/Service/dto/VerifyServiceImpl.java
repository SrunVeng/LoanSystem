package com.mbankingloan.mbankingloan.Feature.Auth.Service.dto;


import com.mbankingloan.mbankingloan.Domain.User;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.UserRepository;
import com.mbankingloan.mbankingloan.Feature.Auth.Repository.EmailRepository;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request.StaffRegisterVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class VerifyServiceImpl implements VerifyService {

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;


    @Override
    public void registerVerify(StaffRegisterVerify staffRegisterVerify) {

        if (!userRepository.existsByEmail(staffRegisterVerify.email())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email is incorrect");
        }

        if (!emailRepository.existsByVerificationCode(staffRegisterVerify.verificationCode())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Verification code is incorrect");
        }
        User user = userRepository.findByEmail(staffRegisterVerify.email());
        user.setIsVerified(true);
        userRepository.save(user);

    }
}
