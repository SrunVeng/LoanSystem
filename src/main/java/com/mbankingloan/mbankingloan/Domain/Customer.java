package com.mbankingloan.mbankingloan.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;


    private LocalDate dateOfBirth;
    //@Pattern(regexp = "^0\\d{9}$", message = "Wrong Phone number format")
    @Size(min = 9, max = 10)
    @Column(nullable = false, unique = true, length = 10)
    private String phoneNumber;
    @Column(nullable = false, unique = true, length = 50)
    private String nationalCardId;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 6)
    private String pin;
    private String name;
    @Column(nullable = false, length = 10)
    private String gender;
    private String profileImageUrl;
    private String oneSignalId;
    @Column(unique = true, length = 50)
    private String StudentIdCard;


    private String cityOrProvince;
    private String companyName;
    private String employeeType;
    private String khanOrDistrict;
    private String mainSourceOfIncome;
    private BigDecimal monthlyIncomeRange;
    private String position;
    private String sangkatOrCommune;
    private String street;
    private String village;

    private LocalDate createdAt;
    private Boolean isVerified;
    private Boolean isBlock;


    //Security
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isDeleted;

    @OneToMany(mappedBy = "customer")
    private List<CustomerAccount> customerAccount;



}
