package com.mbankingloan.mbankingloan.Feature.Auth.Repository;


import com.mbankingloan.mbankingloan.Domain.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailVerification, Integer> {

    Boolean existsByVerificationCode(String verificationCode);


}
