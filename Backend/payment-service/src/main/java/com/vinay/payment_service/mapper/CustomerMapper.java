package com.vinay.payment_service.mapper;

import com.vinay.payment_service.entities.Customer;
import com.vinay.payment_service.model.request.CustomerRequest;
import com.vinay.payment_service.model.response.CustomerResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerMapper {

    public Customer toEntity(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.name());
        customer.setEmail(customerRequest.email());
        customer.setAddress(customerRequest.address());
        customer.setPhone(customerRequest.phone());
        customer.setAmountAvailable(customerRequest.amountAvailable());
        return customer;
    }


    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getAmountAvailable()
        );
    }

    public void mapCustomerWithRequest(Customer customer, CustomerRequest customerRequest) {
        customer.setAddress(customerRequest.address());
        customer.setName(customerRequest.name());
        customer.setEmail(customerRequest.email());
        customer.setPhone(customerRequest.phone());
        customer.setAmountAvailable(customerRequest.amountAvailable());
    }

    public List<CustomerResponse> toListResponse(List<Customer> customers) {
        return customers.stream().map(this::toResponse).toList();
    }
}
