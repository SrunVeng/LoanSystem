package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository;


import com.mbankingloan.mbankingloan.Domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

        boolean existsByCustomerCIFNumber(String customerCIFNumber);

        boolean existsByphoneNumber(String phoneNumber);

        Customer findByphoneNumber(String phoneNumber);

        Customer findByCustomerCIFNumber(String customerCIFNumber);

        List<Customer> findByLastName(String lastName);

}
