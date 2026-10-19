package com.empresa.civishop.backend.Service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.civishop.backend.Entity.Customer;
import com.empresa.civishop.backend.Entity.Order;
import com.empresa.civishop.backend.Entity.Product;
import com.empresa.civishop.backend.Repository.CustomerRepository;
import com.empresa.civishop.backend.Repository.OrderRepository;
import com.empresa.civishop.backend.Repository.ProductRepository;
import com.empresa.civishop.backend.Service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order findRandomOrder() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return orders.get(random.nextInt(orders.size()));
    }

    @Override
    @org.springframework.transaction.annotation.Transactional 
    public Order createOrder(Long customerId, List<java.util.Map<String, Object>> items) {

        java.util.Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent() == false) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }
        Customer customer = customerOptional.get();

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order.setStatus("PENDING");
        order.setTotal(0.0); 

        for (int i = 0; i < items.size(); i++) {
            java.util.Map<String, Object> item = items.get(i);

            Long productId = ((Number) item.get("productId")).longValue();
            int quantity = ((Number) item.get("quantity")).intValue();

            java.util.Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent() == false) {
                throw new IllegalArgumentException("Product not found with ID: " + productId);
            }
            Product product = productOptional.get();

            if (product.getStock() < quantity) {
                throw new IllegalStateException("Not enough stock for: " + product.getName());
            }

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

            order.addProduct(product, quantity);
        }

        return orderRepository.save(order);
    }

}
