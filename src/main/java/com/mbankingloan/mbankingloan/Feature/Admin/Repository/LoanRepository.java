package com.mbankingloan.mbankingloan.Feature.Admin.Repository;

import com.mbankingloan.mbankingloan.Domain.CollateralType;
import com.mbankingloan.mbankingloan.Domain.Loan;
import com.mbankingloan.mbankingloan.Domain.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    Optional<Loan> findByIdAndName(Integer id, String name);

    Boolean existsByName(String name);


}
