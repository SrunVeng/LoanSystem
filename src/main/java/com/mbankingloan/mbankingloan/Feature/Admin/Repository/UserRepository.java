package com.mbankingloan.mbankingloan.Feature.Admin.Repository;

import com.mbankingloan.mbankingloan.Domain.CollateralType;
import com.mbankingloan.mbankingloan.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // to generate staffID prevent Duplicate
    boolean existsByStaffId(String staffId);
    // validateEmail
    boolean existsByEmail(String email);

    User findByEmail(String email);
    // validatePhoneNumber
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByFirstName(String firstName);
    boolean existsByLastName(String lastName);

    Optional<User> findByIdAndFirstNameAndLastName(Integer id, String firstName, String lastName);
    Optional<User> deleteByIdAndFirstNameAndLastName(Integer id, String firstName, String lastName);

    User findByStaffId(String staffId);

}
