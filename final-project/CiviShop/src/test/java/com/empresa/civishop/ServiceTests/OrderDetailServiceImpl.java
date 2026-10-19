package com.empresa.civishop.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.empresa.civishop.backend.Entity.OrderDetail.OrderDetail;
import com.empresa.civishop.backend.Repository.OrderDetailRepository;
import com.empresa.civishop.backend.Service.impl.OrderDetailServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrderDetailServiceImplTest {

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @InjectMocks
    private OrderDetailServiceImpl orderDetailService;

    @Test
    void testSaveNullOrderDetailShouldThrowError() {
        OrderDetail badDetail = null;
        boolean threwError = false;
        try {
            orderDetailService.save(badDetail);
        } catch (Exception e) {
            threwError = true;
        }
        assertEquals(true, threwError);
        verify(orderDetailRepository, never()).save(Mockito.any());
    }
}
