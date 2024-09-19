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

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;

    @OneToOne
    private Branch branchCode;

    private LocalDate birthDate;
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
    private List<Role> roles;

    private LocalDate createdAt;
    private Boolean isVerified;
    private Boolean isBlock;


    //Security
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isDeleted;


}
