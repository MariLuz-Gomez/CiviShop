package com.empresa.civishop.backend.Service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.empresa.civishop.backend.Entity.Product;
import com.empresa.civishop.backend.Repository.ProductRepository;
import com.empresa.civishop.backend.Service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product findRandomProduct() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return products.get(random.nextInt(products.size()));
    }
}
