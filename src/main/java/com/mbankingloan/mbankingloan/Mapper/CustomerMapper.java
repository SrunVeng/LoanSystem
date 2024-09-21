package com.mbankingloan.mbankingloan.Mapper;


import com.mbankingloan.mbankingloan.Domain.Customer;
import com.mbankingloan.mbankingloan.Domain.User;
import com.mbankingloan.mbankingloan.Feature.Admin.Service.dto.Request.UpdateUser;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.CreateCustomerCiF;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.UpdateCustomer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseCustomer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseCustomerDetails;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

        Customer fromCreateCustomerRequest(CreateCustomerCiF createCustomerCiF);

        ResponseCustomer toResponseCustomer(Customer customer);

        ResponseCustomerDetails toResponseCustomerDetails(Customer customer);

        Customer fromUpdateCustomerRequest(UpdateCustomer updateCustomer);

        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        void fromUpdateCustomerRequestPartially(@MappingTarget Customer customer, UpdateCustomer updatecustomer);


        List<ResponseCustomerDetails> toResponseCustomerDetailsList(List<Customer> customers);

}
