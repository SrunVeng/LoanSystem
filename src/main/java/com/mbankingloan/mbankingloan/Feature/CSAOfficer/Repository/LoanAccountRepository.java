package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository;

import com.mbankingloan.mbankingloan.Domain.LoanAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccount, Integer> {




    LoanAccount findByActNo(String actNo);

    Boolean existsByactNo(String actNo);

    Boolean existsByPhoneNumber(String actNo);

    List<LoanAccount> findAllByPhoneNumber(String phoneNumber);



}

