package com.vinay.payment_service.web.controller;

import com.vinay.payment_service.config.logging.Loggable;
import com.vinay.payment_service.exception.CustomerNotFoundException;
import com.vinay.payment_service.model.query.FindCustomersQuery;
import com.vinay.payment_service.model.request.CustomerRequest;
import com.vinay.payment_service.model.response.CustomerResponse;
import com.vinay.payment_service.model.response.PageResult;
import com.vinay.payment_service.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.vinay.payment_service.utils.AppConstants.*;

@RestController
@RequestMapping("/api/customers")
@Loggable
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    PageResult<CustomerResponse> getAllCustomers(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false)
            int pageNo,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false)
            int pageSize,
            @RequestParam(defaultValue = DEFAULT_SORT_BY, required = false)
            String sortBy,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false)
            String sortDir) {

        FindCustomersQuery findCustomersQuery =
                new FindCustomersQuery(pageNo, pageSize, sortBy, sortDir);

        return customerService.findAllCustomers(findCustomersQuery);
    }

    @GetMapping("/{id}")
    ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return customerService
                .findCustomerById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }


    @GetMapping("/name/{name}")
    ResponseEntity<CustomerResponse> getCustomerByName(@PathVariable String name) {
        return customerService
                .findCustomerByName(name)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CustomerNotFoundException(name));
    }

    @PostMapping
    ResponseEntity<CustomerResponse> createCustomer(
            @RequestBody @Valid CustomerRequest customerRequest) {
        CustomerResponse response = customerService.saveCustomer(customerRequest);

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/api/customers/{id}")
                        .buildAndExpand(response.customerId())
                        .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id, @RequestBody @Valid CustomerRequest customerRequest
    ) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerRequest));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable Long id) {
        return customerService
                .findCustomerById(id)
                .map(
                        customer -> {
                            customerService.deleteCustomerById(id);
                            return ResponseEntity.ok(customer);
                        }
                ).orElseThrow(() -> new CustomerNotFoundException(id));
    }
}





