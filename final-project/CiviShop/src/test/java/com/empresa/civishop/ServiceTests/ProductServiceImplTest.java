package com.empresa.civishop.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.empresa.civishop.backend.Entity.Product;
import com.empresa.civishop.backend.Repository.ProductRepository;
import com.empresa.civishop.backend.Service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testFindProductOK() {
        Long productId = 10L;
        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setName("Mouse");
        mockProduct.setPrice(20.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        Optional<Product> result = productService.findById(productId);
        assertEquals(true, result.isPresent());
        assertEquals("Mouse", result.get().getName());
        assertEquals(20.0, result.get().getPrice());
        verify(productRepository).findById(productId);
    }

    @Test
    void testSaveNullProductError() {
        Product badProduct = null;
        boolean threwError = false;
        try {
            productService.save(badProduct);
        } catch (Exception e) {
            threwError = true;
        }
        assertEquals(true, threwError);
        verify(productRepository, never()).save(Mockito.any(Product.class));
    }
}
