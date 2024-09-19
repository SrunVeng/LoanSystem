package com.mbankingloan.mbankingloan.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "phone_verification")
public class PhoneVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Customer customer;


    private String verificationCode;

    private LocalTime expiryTime;

}
