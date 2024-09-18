package com.mbankingloan.mbankingloan.Domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customer_accounts")
public class CustomerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    private Customer customer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Account account;


    private Boolean isBlocked;
    private Boolean isDeleted;
    private LocalDate createdAt;

}
