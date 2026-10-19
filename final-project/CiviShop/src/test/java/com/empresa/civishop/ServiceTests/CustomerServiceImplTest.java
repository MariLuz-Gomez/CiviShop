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

import com.empresa.civishop.backend.Entity.Customer;
import com.empresa.civishop.backend.Repository.CustomerRepository;
import com.empresa.civishop.backend.Service.impl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testRegisterNullCustomerShouldThrowError() {
        Customer badCustomer = null;
        boolean threwError = false;
        try {
            customerService.save(badCustomer);
        } catch (Exception e) {
            threwError = true; 
        }
        assertEquals(true, threwError);
        verify(customerRepository, never()).save(Mockito.any());
    }
}
