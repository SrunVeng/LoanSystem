package com.mbankingloan.mbankingloan.Feature.CSAOfficer.Controller;


import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.CustomerService;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.CreateCustomerCiF;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Request.UpdateCustomer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseCustomer;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Service.dto.Response.ResponseCustomerDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/csaOfficer/api/v1/customerCif")
public class CustomerControllerCSA {

    private final CustomerService customerService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    ResponseCustomer createCustomer(@Valid @RequestBody CreateCustomerCiF createCustomerCiF) {
        return customerService.createCustomer(createCustomerCiF);

    }
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/update")
    ResponseCustomerDetails updateCustomer(@Valid @RequestBody UpdateCustomer updateCustomer) {
        return customerService.updateCustomer(updateCustomer);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getCustomerByCifNumber/{cif}")
    ResponseCustomerDetails getCustomerByCifNumber(@PathVariable String cif) {
        return customerService.getCustomerByCifNumber(cif);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getCustomerBylastName/{lastName}")
    List<ResponseCustomerDetails> getCustomerBylastName(@RequestParam String lastName) {
        return customerService.getCustomerByLastName(lastName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getCustomerByPhoneNumber/{PhoneNumber}")
    ResponseCustomerDetails getCustomerByPhoneNumber(@PathVariable String PhoneNumber) {
        return customerService.getCustomerByPhoneNumber(PhoneNumber);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/deleteCustomerByCif/{Cif}")
    ResponseCustomerDetails deleteCustomerByCif(@PathVariable String Cif) {
        return customerService.deleteCustomerByCif(Cif);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/recoverCustomerByCif/{Cif}")
    ResponseCustomerDetails recoverCustomerByCif(@PathVariable String Cif) {
        return customerService.recoverCustomerByCif(Cif);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAllCustomer")
    List<ResponseCustomerDetails> getAllCustomer() {
        return customerService.getAllCustomer();
    }


}
