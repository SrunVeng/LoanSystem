package com.mbankingloan.mbankingloan.Feature.Admin.Repository;

import com.mbankingloan.mbankingloan.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // to generate staffID prevent Duplicate
    boolean existsByStaffId(String staffId);
    // validateEmail
    boolean existsByemail(String email);
    // validatePhoneNumber
    boolean existsByphoneNumber(String phoneNumber);

    boolean existsByfirstName(String firstName);
    boolean existsBylastName(String lastName);

}
