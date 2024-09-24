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

    private String staffId;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(length = 10, unique = true, nullable = false)
    private String phoneNumber;

    @ManyToOne(optional = false)
    private Branch branchCode;

    @ManyToMany
    List<Customer> customers;

    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private LocalDate hireDate;

    private String provinceCity;
    private String KhanDistrict;
    private String village;
    private String country;

    private String position;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @Column(nullable = false)
    private List<Role> roles;


    //System Generate
    private LocalDate createdAt;
    private Boolean isVerified;
    private Boolean isBlock;


    //Security
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isDeleted;


}
