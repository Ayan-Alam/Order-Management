package com.grocery.service;

import com.grocery.exception.ResourceNotFoundException;
import com.grocery.model.Customer;
import com.grocery.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    
    @Mock
    private CustomerRepository customerRepository;
    
    @InjectMocks
    private CustomerService customerService;
    
    private Customer customer;
    
    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId("1");
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setAddress("123 Main St");
        customer.setPhone("1234567890");
    }
    
    @Test
    void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(customer);
        when(customerRepository.findAll()).thenReturn(customers);
        
        List<Customer> result = customerService.getAllCustomers();
        
        assertEquals(1, result.size());
        verify(customerRepository, times(1)).findAll();
    }
    
    @Test
    void testGetCustomerById_Success() {
        when(customerRepository.findById("1")).thenReturn(Optional.of(customer));
        
        Customer result = customerService.getCustomerById("1");
        
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(customerRepository, times(1)).findById("1");
    }
    
    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById("999")).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            customerService.getCustomerById("999");
        });
    }
    
    @Test
    void testCreateCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        
        Customer result = customerService.createCustomer(customer);
        
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(customerRepository, times(1)).save(customer);
    }
    
    @Test
    void testUpdateCustomer() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Jane Doe");
        updatedCustomer.setEmail("jane@example.com");
        updatedCustomer.setAddress("456 Oak Ave");
        updatedCustomer.setPhone("9876543210");
        
        when(customerRepository.findById("1")).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        
        Customer result = customerService.updateCustomer("1", updatedCustomer);
        
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        verify(customerRepository, times(1)).save(customer);
    }
    
    @Test
    void testDeleteCustomer() {
        when(customerRepository.findById("1")).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).delete(customer);
        
        customerService.deleteCustomer("1");
        
        verify(customerRepository, times(1)).delete(customer);
    }
}