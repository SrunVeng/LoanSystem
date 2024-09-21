package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto;


import com.mbankingloan.mbankingloan.Domain.Customer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.CustomerRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.CreateCustomerCiF;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.UpdateCustomer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseCustomer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseCustomerDetails;
import com.mbankingloan.mbankingloan.Mapper.CustomerMapper;
import com.mbankingloan.mbankingloan.Util.GenerateCustomerCIFNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final GenerateCustomerCIFNumber generateCustomerCIFNumber;
    private final CustomerMapper customerMapper;


    @Override
    @Transactional
    public ResponseCustomer createCustomer(CreateCustomerCiF createCustomerCiF) {
        Customer newCustomer = customerMapper.fromCreateCustomerRequest(createCustomerCiF);
        //setSecurity
        newCustomer.setIsAccountNonExpired(true);
        newCustomer.setIsAccountNonLocked(true);
        newCustomer.setIsCredentialsNonExpired(true);
        newCustomer.setIsDeleted(false);

        // create Date
        newCustomer.setCreatedAt(LocalDate.now());
        newCustomer.setIsVerified(false);
        newCustomer.setIsBlock(true); // set to false when Customer set their own password

        // SetPasswordAndPin
        newCustomer.setCustomerCIFNumber(generateCustomerCIFNumber.generateCustomerCIFNumber());
        // setUser and Branch After Implement Security
        // Branch
        // User
        customerRepository.save(newCustomer);
        return customerMapper.toResponseCustomer(newCustomer);
    }

    @Override
    @Transactional
    public ResponseCustomerDetails updateCustomer(UpdateCustomer updateCustomer) {

        if (!customerRepository.existsByCustomerCIFNumber(updateCustomer.customerCIFNumber())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CustomerCIF not found");
        }
        Customer customer = customerRepository.findByCustomerCIFNumber(updateCustomer.customerCIFNumber());

        customerMapper.fromUpdateCustomerRequestPartially(customer, updateCustomer);
        customerRepository.save(customer);

        return customerMapper.toResponseCustomerDetails(customer);
    }

    @Override
    public ResponseCustomerDetails getCustomerByCifNumber(String cif) {

        if (!customerRepository.existsByCustomerCIFNumber(cif)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CustomerCIF not found");
        }
        return customerMapper.toResponseCustomerDetails(customerRepository.findByCustomerCIFNumber(cif));
    }

    @Override
    public List<ResponseCustomerDetails> getAllCustomer() {
        return customerMapper.toResponseCustomerDetailsList(customerRepository.findAll());
    }

    @Override
    public List<ResponseCustomerDetails> getCustomerByLastName(String lastName) {
        return customerMapper.toResponseCustomerDetailsList(customerRepository.findByLastName(lastName));
    }

    @Override
    public ResponseCustomerDetails getCustomerByPhoneNumber(String phoneNumber) {

        if (!customerRepository.existsByphoneNumber(phoneNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PhoneNumber not found");
        }

        return customerMapper.toResponseCustomerDetails(customerRepository.findByphoneNumber(phoneNumber));
    }

    @Override
    @Transactional
    public ResponseCustomerDetails deleteCustomerByCif(String cif) {

        if (!customerRepository.existsByCustomerCIFNumber(cif)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CustomerCIF not found");
        }

        Customer customer = customerRepository.findByCustomerCIFNumber(cif);
        customer.setIsDeleted(true);
        customerRepository.save(customer);
        return customerMapper.toResponseCustomerDetails(customer);
    }

    @Override
    @Transactional
    public ResponseCustomerDetails recoverCustomerByCif(String cif) {
        if (!customerRepository.existsByCustomerCIFNumber(cif)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CustomerCIF not found");
        }

        Customer customer = customerRepository.findByCustomerCIFNumber(cif);
        customer.setIsDeleted(false);
        customerRepository.save(customer);
        return customerMapper.toResponseCustomerDetails(customer);
    }

}
