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

    // System Generate
    @ManyToMany
    private List<Customer> customer;

    @Column(length = 12, unique = true, nullable = false)
    private String actNo;


    @OneToOne(cascade = CascadeType.ALL)
    private LoanApplication LoanApplication;

    private String phoneNumber;



    // balance will set to LoanAmount of loanApplication if isDrawdown is true
    private BigDecimal balance;

    //system will default set to false unless user set new password
    private Boolean isActive;

    // isDeleted canSet to false is OutstandingBalance in LoanApplication is 0
    private Boolean isDeleted;



    @ManyToOne
    private LoanAccountType loanAccountType;



    private LocalDate CreatedAt;


    @OneToMany(mappedBy = "ownerId")
    private List<Transaction> transactionOwner;

}
