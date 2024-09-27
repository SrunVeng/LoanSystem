package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto;


import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.CreateCustomerCiF;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.UpdateCustomer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseCustomer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseCustomerDetails;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerService {


    @Transactional
    ResponseCustomer createCustomer(CreateCustomerCiF createCustomerCiF, String staffId);

    ResponseCustomerDetails updateCustomer(@Valid UpdateCustomer updateCustomer);

    ResponseCustomerDetails getCustomerByCifNumber(String cif);

    List<ResponseCustomerDetails> getAllCustomer();

    List<ResponseCustomerDetails> getCustomerByLastName(String lastName);

    ResponseCustomerDetails getCustomerByPhoneNumber(String phoneNumber);

    ResponseCustomerDetails deleteCustomerByCif(String cif);

    ResponseCustomerDetails recoverCustomerByCif(String cif);


    Page<ResponseCustomerDetails> getCustomersPagination(int page, int size);
}
