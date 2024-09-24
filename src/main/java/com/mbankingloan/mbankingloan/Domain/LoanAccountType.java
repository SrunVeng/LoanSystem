package com.mbankingloan.mbankingloan.Domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "loan_account_types")
public class LoanAccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private Boolean isDeleted;

    @Column(nullable = false, unique = true, length = 50)
    private String name;


    @OneToMany(mappedBy = "loanAccountType")
    @JsonIgnore
    private List<LoanAccount> loanAccounts;


}
