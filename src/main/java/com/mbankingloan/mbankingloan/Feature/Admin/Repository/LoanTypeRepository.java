package com.mbankingloan.mbankingloan.Feature.Admin.Repository;

import com.mbankingloan.mbankingloan.Domain.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanTypeRepository extends JpaRepository<LoanType, Integer> {

        boolean existsByName(String name);
        boolean existsById(int id);

}
