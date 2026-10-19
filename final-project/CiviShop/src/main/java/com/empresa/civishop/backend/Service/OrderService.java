package com.empresa.civishop.backend.Service;

import java.util.List;
import java.util.Optional;

import com.empresa.civishop.backend.Entity.Order;

public interface OrderService {
    List<Order> findAll();

    Optional<Order> findById(Long id);

    Order save(Order order);

    void deleteById(Long id);

    Order findRandomOrder();

    Order createOrder(Long customerId, List<java.util.Map<String, Object>> items);
}
