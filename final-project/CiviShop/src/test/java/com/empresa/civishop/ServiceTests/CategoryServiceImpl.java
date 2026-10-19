package com.empresa.civishop.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.empresa.civishop.backend.Entity.Category;
import com.empresa.civishop.backend.Repository.CategoryRepository;
import com.empresa.civishop.backend.Service.impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void testFindCategoryThatDoesNotExist() {
        Long fakeId = 888L;
        when(categoryRepository.findById(fakeId)).thenReturn(Optional.empty());
        Optional<Category> result = categoryService.findById(fakeId);
        assertEquals(false, result.isPresent());
        verify(categoryRepository).findById(fakeId);
    }
}
