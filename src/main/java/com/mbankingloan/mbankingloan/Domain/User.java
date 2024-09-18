package com.mbankingloan.mbankingloan.Domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer staffId;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String branchCode;
    private LocalDate birthDate;
    private LocalDate hireDate;

    private String provinceCity;
    private String KhanDistrict;
    private String village;
    private String country;

    private String position;

    @OneToMany
    private List<Role> roles;


}
