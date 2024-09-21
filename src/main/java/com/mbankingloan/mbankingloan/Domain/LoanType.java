package com.mbankingloan.mbankingloan.Domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "loantypes")
public class LoanType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Boolean isDeleted;

//    @OneToMany(mappedBy = "loantype")
//    private List<Loan> loans;


}
