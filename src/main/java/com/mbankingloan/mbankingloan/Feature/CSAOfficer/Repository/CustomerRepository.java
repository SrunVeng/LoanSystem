package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository;


import com.mbankingloan.mbankingloan.Domain.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

        boolean existsByCustomerCIFNumber(String customerCIFNumber);

        boolean existsByphoneNumber(String phoneNumber);

        List<Customer> findAllBycustomerCIFNumber(String customerCIFNumber);

        Customer findByphoneNumber(String phoneNumber);

        Customer findByCustomerCIFNumber(String customerCIFNumber);

        List<Customer> findByLastName(String lastName);

        Page<Customer> findAll(Pageable pageable);

        List<Customer> findAllByCustomerCIFNumberIn(List<String> customerCifNumbers);


        Boolean existsAllByCustomerCIFNumberIn(List<String> customerCifNumbers);

    boolean existsByEmail(@NotNull(message = "Email is required") @Email String email);

        Customer findByEmail(String email);


        List<Customer> findAllByphoneNumber(String username);
}
