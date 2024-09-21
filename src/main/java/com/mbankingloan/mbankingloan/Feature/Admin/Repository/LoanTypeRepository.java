package com.mbankingloan.mbankingloan.Feature.Admin.Repository;

import com.mbankingloan.mbankingloan.Domain.Loan;
import com.mbankingloan.mbankingloan.Domain.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LoanTypeRepository extends JpaRepository<LoanType, Integer> {

        boolean existsByName(String name);


        Optional<LoanType> findByIdAndName(Integer id, String name);

}
