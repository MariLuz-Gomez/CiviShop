package com.empresa.civishop.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.empresa.civishop.backend.Entity.Order;
import com.empresa.civishop.backend.Entity.Product;
import com.empresa.civishop.backend.Repository.OrderRepository;
import com.empresa.civishop.backend.Repository.ProductRepository;
import com.empresa.civishop.backend.Service.impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void testCreateOrderOK() {
        Long customerId = 1L;
        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("productId", 1);
        item.put("quantity", 2);
        items.add(item);

        Order mockSavedOrder = new Order();
        mockSavedOrder.setId(99L);
        mockSavedOrder.setStatus("PENDING");
        mockSavedOrder.setTotal(50.0);

        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(mockSavedOrder);
        Order result = orderService.createOrder(customerId, items);
        assertNotNull(result);
        assertEquals(99L, result.getId());
        assertEquals("PENDING", result.getStatus());
        assertEquals(50.0, result.getTotal());
        verify(orderRepository).save(Mockito.any(Order.class));
    }

    @Test
    void testCreateOrderNoStockError() {
        Long customerId = 1L;
        Long productId = 5L;

        List<Map<String, Object>> cart = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("productId", productId);
        item.put("quantity", 10); 
        cart.add(item);
        Product warehouseProduct = new Product();
        warehouseProduct.setId(productId);
        warehouseProduct.setStock(2); 
        Mockito.lenient()
               .when(productRepository.findById(productId))
               .thenReturn(Optional.of(warehouseProduct));
        boolean threwError = false;
        try {
            orderService.createOrder(customerId, cart);
        } catch (Exception e) {
            threwError = true; 
        }
        assertEquals(true, threwError);
        verify(orderRepository, never()).save(Mockito.any());
    }
}
