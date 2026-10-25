package com.vinay.payment_service.respositories;

import com.vinay.payment_service.entities.Customer;
import com.vinay.payment_service.model.response.CustomerResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Optional<CustomerResponse> findByName(String name);
    Optional<Customer> findById(Long customerId);
    Customer save(Customer customer);
    void deleteById(Long id);
    Page<Customer> findAll(Pageable pageable);
    List<Customer> saveAll(List<Customer> customers);
    void deleteAll();

    Optional<Customer> findByEmail(@NotBlank(message = "Email cannot be Blank") @Email(message = "supplied email is not valid") String email);
}
