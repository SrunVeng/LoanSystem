package com.mbankingloan.mbankingloan.Feature.Admin.Repository;

import com.mbankingloan.mbankingloan.Domain.Loan;
import com.mbankingloan.mbankingloan.Domain.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Integer> {


    boolean existsByLoan_Id(Integer loanId);



}
