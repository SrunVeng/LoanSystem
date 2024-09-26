package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Controller;


import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.CustomerService;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.CreateCustomerCiF;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.UpdateCustomer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseCustomer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseCustomerDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/csaOfficer/api/v1/customerCif")
public class CustomerControllerCSA {

    private final CustomerService customerService;


    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    ResponseCustomer createCustomer(@Valid @RequestBody CreateCustomerCiF createCustomerCiF) {
        return customerService.createCustomer(createCustomerCiF);

    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/update")
    ResponseCustomerDetails updateCustomer(@Valid @RequestBody UpdateCustomer updateCustomer) {
        return customerService.updateCustomer(updateCustomer);
    }


    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_LOAN-OFFICER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getCustomerByCifNumber/{cif}")
    ResponseCustomerDetails getCustomerByCifNumber(@PathVariable String cif) {
        return customerService.getCustomerByCifNumber(cif);
    }

    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_LOAN-OFFICER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getCustomerBylastName/{lastName}")
    List<ResponseCustomerDetails> getCustomerBylastName(@RequestParam String lastName) {
        return customerService.getCustomerByLastName(lastName);
    }

    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_LOAN-OFFICER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getCustomerByPhoneNumber/{PhoneNumber}")
    ResponseCustomerDetails getCustomerByPhoneNumber(@PathVariable String PhoneNumber) {
        return customerService.getCustomerByPhoneNumber(PhoneNumber);
    }

    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/deleteCustomerByCif/{Cif}")
    ResponseCustomerDetails deleteCustomerByCif(@PathVariable String Cif) {
        return customerService.deleteCustomerByCif(Cif);
    }

    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/recoverCustomerByCif/{Cif}")
    ResponseCustomerDetails recoverCustomerByCif(@PathVariable String Cif) {
        return customerService.recoverCustomerByCif(Cif);
    }


    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_LOAN-OFFICER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllCustomer")
    List<ResponseCustomerDetails> getAllCustomer() {
        return customerService.getAllCustomer();
    }

    @PreAuthorize("hasAnyAuthority( 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_LOAN-OFFICER', 'SCOPE_ROLE_CSA-OFFICER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllCustomerPagination")
    Page<ResponseCustomerDetails> getAllCustomerPagination(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return customerService.getCustomersPagination(page, size);
    }




}
