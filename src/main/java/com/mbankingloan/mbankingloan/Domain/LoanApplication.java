package com.mbankingloan.mbankingloan.Domain;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "loan_applications")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    Loan loan;

    @ManyToOne
    LoanType loanType;

    @OneToMany
    List<User> users;

    private BigDecimal Balance;

    private Integer tenure;

    private Integer remainTenure;

    private BigDecimal outstandingBalance;

    private BigDecimal interestRate;

    private BigDecimal monthlyInstallment;

    private String description;


}
