package com.mbankingloan.mbankingloan.Config.Security;

import com.mbankingloan.mbankingloan.Domain.Customer;

import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;




@Service("customerDetailsService")
@RequiredArgsConstructor
public class CustomerDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find customer by phone number
        Customer customer = customerRepository.findByphoneNumber(username);
        // Throw exception if the customer is not found
        if (customer == null) {
            throw new UsernameNotFoundException("Customer with phone number " + username + " not found");
        }
        // Populate CustomCustomerDetails with customer data
        CustomCustomerDetails customCustomerDetails = new CustomCustomerDetails();
        customCustomerDetails.setCustomer(customer);
        return customCustomerDetails;
    }
}
