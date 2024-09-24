package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository;

import com.mbankingloan.mbankingloan.Domain.LoanAccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanAccountTypeRepository extends JpaRepository<LoanAccountType, Integer> {



}
