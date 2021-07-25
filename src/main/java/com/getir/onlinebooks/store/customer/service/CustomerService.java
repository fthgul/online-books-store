package com.getir.onlinebooks.store.customer.service;

import com.getir.onlinebooks.store.customer.entity.Customer;

public interface CustomerService {
    Customer saveCustomer(Customer customer);

    Customer findCustomerById(Integer customerId);
}
