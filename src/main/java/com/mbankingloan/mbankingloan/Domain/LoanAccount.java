package com.mbankingloan.mbankingloan.Domain;



import jakarta.persistence.*;
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
@Table(name = "loan_accounts")
public class LoanAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    private List<Customer> customer;

    @Column(length = 12, unique = true, nullable = false)
    private String actNo;


    @OneToOne(cascade = CascadeType.ALL)
    private LoanApplication LoanApplication;

    private String phoneNumber;



    private BigDecimal balance;

    private Boolean isActive;

    private Boolean isDeleted;



    @ManyToOne
    private User user;



    private LocalDate CreatedAt;



}
