package com.empresa.civishop.backend.Service;

import java.util.List;
import java.util.Optional;

import com.empresa.civishop.backend.Entity.Customer;

public interface CustomerService {
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    Customer save(Customer customer);
    void deleteById(Long id);
    Optional<Customer> findByPhone(String phone);
}
