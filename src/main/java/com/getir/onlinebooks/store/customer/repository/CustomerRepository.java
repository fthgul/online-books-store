package com.getir.onlinebooks.store.customer.repository;

import com.getir.onlinebooks.store.customer.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    boolean existsByEmail(String email);
}
