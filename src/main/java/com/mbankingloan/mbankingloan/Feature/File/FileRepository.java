package com.mbankingloan.mbankingloan.Feature.File;


import com.mbankingloan.mbankingloan.Domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

    File findByuser_id(Integer userId);

    File findBycustomer_id(Integer userId);

}
