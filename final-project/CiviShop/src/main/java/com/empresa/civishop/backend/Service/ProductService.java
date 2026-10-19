package com.empresa.civishop.backend.Service;

import java.util.List;
import java.util.Optional;

import com.empresa.civishop.backend.Entity.Product;

public interface ProductService {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    void deleteById(Long id);
    Optional<Product> findByName(String name);
    Product findRandomProduct();
}
