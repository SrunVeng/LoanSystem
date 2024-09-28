package com.mbankingloan.mbankingloan.Domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "loan_applications")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    private Loan loan;



    @ManyToOne
    @JsonIgnore
    private User user;


    @OneToMany
    private List<Customer> customer;

    private BigDecimal requestAmount;

    private Integer tenure;


    @ManyToMany
    private List<CollateralType> collateralTypes;

    private BigDecimal totalCollateralValue;

    private BigDecimal maxLoanableAmount;

    private BigDecimal interestRate;


    private LocalDate maturityDate;

    private BigDecimal monthlyInstallment;

    private BigDecimal moa;


    private LocalDate createdAt;

    private Boolean isApprovedByBranchManager;
    private Boolean isApprovedByHeadOfLoan;


    private Boolean isDrawDown;


    private Boolean isRejectedByBM;
    private Boolean isRejectedByHeadOfLoan;


}
