package com.empresa.civishop.backend.Service;

import java.util.List;

import java.util.Optional;

import com.empresa.civishop.backend.Entity.Category;

public interface CategoryService {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category save(Category category);
    void deleteById(Long id);
}
