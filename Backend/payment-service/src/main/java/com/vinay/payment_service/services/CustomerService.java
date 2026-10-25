package com.vinay.payment_service.services;

import com.vinay.payment_service.config.logging.Loggable;
import com.vinay.payment_service.entities.Customer;
import com.vinay.payment_service.exception.CustomerNotFoundException;
import com.vinay.payment_service.mapper.CustomerMapper;
import com.vinay.payment_service.model.query.FindCustomersQuery;
import com.vinay.payment_service.model.request.CustomerRequest;
import com.vinay.payment_service.model.response.CustomerResponse;
import com.vinay.payment_service.model.response.PageResult;
import com.vinay.payment_service.respositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@RequiredArgsConstructor
@Loggable
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public PageResult<CustomerResponse> findAllCustomers(FindCustomersQuery findCustomersQuery) {
        log.info(
                "Fetching findAllCustomers for pageNo {} with pageSize {}, sorting By {} {}",
                findCustomersQuery.pageNo() - 1,
                findCustomersQuery.pageSize(),
                findCustomersQuery.sortBy(),
                findCustomersQuery.sortDir()
        );

        Pageable pageable = createPageable(findCustomersQuery);
        Page<Customer> page = customerRepository.findAll(pageable);

        List<CustomerResponse> customerResponses = customerMapper.toListResponse(page.getContent());
        return new PageResult<CustomerResponse>(customerResponses, page);
    }

    private Pageable createPageable(FindCustomersQuery findCustomersQuery) {
        int pageNo = Math.max(findCustomersQuery.pageNo() - 1, 0);
        Sort sort = Sort.by(
                findCustomersQuery.sortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
                        ? Sort.Order.asc(findCustomersQuery.sortBy())
                        : Sort.Order.desc(findCustomersQuery.sortBy())
        );
        return PageRequest.of(pageNo, findCustomersQuery.pageSize(), sort);
    }

    public Optional<CustomerResponse> findCustomerById(Long id) {
        return customerRepository.findById(id).map(customerMapper::toResponse);
    }

    public Optional<CustomerResponse> findCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    public CustomerResponse saveCustomer(CustomerRequest customerRequest) {
        Optional<Customer> customerByEmail =
                customerRepository.findByEmail(customerRequest.email());
        Customer customer;
        if (customerByEmail.isPresent()) {
            customer = customerByEmail.get();
        }else {
            Customer requestCustomer = customerMapper.toEntity(customerRequest);
            customer = customerRepository.save(requestCustomer);
        }

        return customerMapper.toResponse(customer);
    }


    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {
        Customer customer =
                customerRepository
                        .findById(id)
                        .orElseThrow(() -> new CustomerNotFoundException(id));

        // Update the customer object with data from customerRequest
        customerMapper.mapCustomerWithRequest(customer, customerRequest);

        // Save the updated customer object
        Customer updatedCustomer = customerRepository.save(customer);

        // Map the updated customer to a response object and return it
        return customerMapper.toResponse(updatedCustomer);
    }

    @Transactional
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }


}
