package com.mbankingloan.mbankingloan.Feature.Admin.Repository;

import com.mbankingloan.mbankingloan.Domain.CollateralType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CollateralTypeRepository extends JpaRepository<CollateralType, Integer> {


    boolean existsByTitle(String title);

    Optional<CollateralType> findByIdAndTitle(Integer id, String title);


}
