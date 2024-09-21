package com.mbankingloan.mbankingloan.Feature.Admin.Repository;

import com.mbankingloan.mbankingloan.Domain.Branch;

import com.mbankingloan.mbankingloan.Domain.Loan;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {


    boolean existsByName(String name);

    boolean existsByCode(String code);

    Optional<Branch> findByIdAndCode(Integer id, String code);

}
