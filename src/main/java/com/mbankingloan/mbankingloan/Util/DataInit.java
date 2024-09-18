package com.mbankingloan.mbankingloan.Util;


import com.mbankingloan.mbankingloan.Domain.LoanType;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.LoanTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final LoanTypeRepository loanTypeRepository;

    @PostConstruct
    void init() {
        LoanTypeInit();
    }

    private void LoanTypeInit(){

        LoanType loanType1 = LoanType.builder()
                .name("TermLoan")
                .build();

        LoanType loanType2 = LoanType.builder()
                .name("OverDaft")
                .build();

        LoanType loanType3 = LoanType.builder()
                .name("HomeLoan")
                .build();
        loanTypeRepository.saveAll(List.of(loanType1, loanType2, loanType3));
    }



}
