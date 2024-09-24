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
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private BigDecimal amountLimit;

    @ManyToMany
    private List<CollateralType> collateralTypes;



    private BigDecimal interestRate;


    @ManyToOne
    private LoanType loantype;

    private Boolean isDeleted;




}
