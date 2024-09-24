package com.mbankingloan.mbankingloan.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String customerCIFNumber;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    private LocalDate dateOfBirth;
    //@Pattern(regexp = "^0\\d{9}$", message = "Wrong Phone number format")
    @Size(min = 9, max = 10)
    @Column(nullable = false, unique = true, length = 10)
    private String phoneNumber;
    @Column(nullable = false, unique = true, length = 50)
    private String nationalCardId;
    @Column(unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String gender;
    private String profileImageUrl;



    private String cityOrProvince;
    private String companyName;
    private String employeeType;
    private String khanOrDistrict;
    private String mainSourceOfIncome;
    private BigDecimal monthlyIncome;
    private String position;
    private String sangKatOrCommune;
    private String village;
    private String street;


    private LocalDate createdAt;
    private Boolean isVerified;
    private Boolean isBlock;



    //Security
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isDeleted;



    @OneToOne
    @JsonIgnore
    private Branch branchCode;

    @ManyToMany
    private List<User> use;


}
