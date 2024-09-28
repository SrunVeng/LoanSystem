package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto;


import com.mbankingloan.mbankingloan.Domain.Customer;
import com.mbankingloan.mbankingloan.Domain.Role;
import com.mbankingloan.mbankingloan.Domain.User;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.RoleRepository;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.UserRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.CustomerRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.CreateCustomerCiF;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.UpdateCustomer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseCustomer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseCustomerDetails;
import com.mbankingloan.mbankingloan.Feature.File.FileRepository;
import com.mbankingloan.mbankingloan.Mapper.CustomerMapper;
import com.mbankingloan.mbankingloan.Util.GenerateCustomerCIFNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final GenerateCustomerCIFNumber generateCustomerCIFNumber;
    private final RoleRepository roleRepository;
    private final CustomerMapper customerMapper;


    @Transactional
    @Override
    public ResponseCustomer createCustomer(CreateCustomerCiF createCustomerCiF, String staffId) {
        Customer newCustomer = customerMapper.fromCreateCustomerRequest(createCustomerCiF);
        User user = userRepository.findByStaffId(staffId);

        //setSecurity
        newCustomer.setIsAccountNonExpired(true);
        newCustomer.setUser(user);
        newCustomer.setIsAccountNonLocked(true);
        newCustomer.setIsCredentialsNonExpired(true);
        newCustomer.setIsDeleted(false);
        // create Date
        newCustomer.setCreatedAt(LocalDate.now());
        newCustomer.setIsVerified(false);
        newCustomer.setIsBlock(false);
        newCustomer.setRoles(roleRepository.findById(5).orElseThrow());
        newCustomer.setPassword("123456");
        newCustomer.setPin("1234");
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
    public ResponseCustomerDetails updateCustomer(UpdateCustomer updateCustomer,String staffId) {

        if (!customerRepository.existsByCustomerCIFNumber(updateCustomer.customerCIFNumber())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CustomerCIF not found");
        }
        Customer customer = customerRepository.findByCustomerCIFNumber(updateCustomer.customerCIFNumber());

        customerMapper.fromUpdateCustomerRequestPartially(customer, updateCustomer);
        customer.setUser(userRepository.findByStaffId(staffId));
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
    public ResponseCustomerDetails deleteCustomerByCif(String cif,String staffId) {

        if (!customerRepository.existsByCustomerCIFNumber(cif)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CustomerCIF not found");
        }

        Customer customer = customerRepository.findByCustomerCIFNumber(cif);
        customer.setIsDeleted(true);
        customer.setUser(userRepository.findByStaffId(staffId));
        customerRepository.save(customer);
        return customerMapper.toResponseCustomerDetails(customer);
    }

    @Override
    @Transactional
    public ResponseCustomerDetails recoverCustomerByCif(String cif,String staffId) {
        if (!customerRepository.existsByCustomerCIFNumber(cif)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CustomerCIF not found");
        }

        Customer customer = customerRepository.findByCustomerCIFNumber(cif);
        customer.setIsDeleted(false);
        customer.setUser(userRepository.findByStaffId(staffId));
        customerRepository.save(customer);
        return customerMapper.toResponseCustomerDetails(customer);
    }

    @Override
    public Page<ResponseCustomerDetails> getCustomersPagination(int page, int size) {
        return customerMapper.toResponseCustomerDetailsPage(customerRepository.findAll(PageRequest.of(page, size)));
    }

}
