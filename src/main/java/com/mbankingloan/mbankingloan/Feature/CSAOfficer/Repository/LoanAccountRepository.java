package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository;

import com.mbankingloan.mbankingloan.Domain.Customer;
import com.mbankingloan.mbankingloan.Domain.LoanAccount;
import com.mbankingloan.mbankingloan.Domain.LoanAccountType;
import com.mbankingloan.mbankingloan.Domain.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccount, Integer> {




    Boolean existsByactNo(String actNo);
}

