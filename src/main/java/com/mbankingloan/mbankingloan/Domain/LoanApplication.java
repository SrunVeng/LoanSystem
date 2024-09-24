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
@Table(name = "loan_applications")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Micro_Loan , large .... loan ?
    @ManyToOne
    Loan loan;

    // one loanApplication can be TermLoan,OD or HomeLoan
    @ManyToOne
    private LoanType loanType;

    // one loan process by one loan officer , one manager approved and one DrawDown by CSA officer
    @OneToMany
    List<User> users;

    // Customer can joint loan
    @OneToMany
    private List<Customer> customer;

    private BigDecimal requestAmount;

    private Integer tenure;

    //system generate by ID input
    @ManyToMany
    private List<CollateralType> collateralTypes;

    private BigDecimal totalCollateralValue;

    private BigDecimal maxLoanableAmount;

    private BigDecimal interestRate;

    //system generate
    private LocalDate maturityDate;
    //system generate
    private BigDecimal monthlyInstallment;
    //system generate
    private BigDecimal moa;
    //system generate
    private BigDecimal downPayment;

    private LocalDate createdAt;

    private Boolean isApprovedByBranchManager;
    private Boolean isApprovedByHeadOfLoan;

    //LoanCanDrawDown can set to true if isApprovedBoth is True
    private Boolean isDrawDown;


    private Boolean isRejectedByBM;
    private Boolean isRejectedByHeadOfLoan;



}
