package com.getir.onlinebooks.store.customer.service.impl;

import com.getir.onlinebooks.store.customer.entity.Customer;
import com.getir.onlinebooks.store.customer.exception.UniqueEmailViolationException;
import com.getir.onlinebooks.store.customer.repository.CustomerRepository;
import com.getir.onlinebooks.store.customer.service.CustomerService;
import com.getir.onlinebooks.store.order.exception.CustomerEntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        final boolean isExistEmail = customerRepository.existsByEmail(customer.getEmail());
        if(isExistEmail) {
            throw new UniqueEmailViolationException("this email has already registered.");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer findCustomerById(Integer customerId) {
        Supplier<CustomerEntityNotFoundException> s =
                () -> new CustomerEntityNotFoundException(customerId + " customerId: not found");
        return customerRepository.findById(customerId).orElseThrow(s);
    }
}
