package com.grocery.service;

import com.grocery.exception.ResourceNotFoundException;
import com.grocery.model.Customer;
import com.grocery.model.GroceryItem;
import com.grocery.model.Order;
import com.grocery.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final GroceryItemService groceryItemService;
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }
    
    public List<Order> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    
    public Order createOrder(Order order) {
        // Validate customer exists
        Customer customer = customerService.getCustomerById(order.getCustomer().getId());
        order.setCustomer(customer);
        
        // Validate grocery items exist and calculate total
        double totalPrice = 0.0;
        for (Order.OrderItem orderItem : order.getOrderItems()) {
            GroceryItem item = groceryItemService.getGroceryItemById(orderItem.getGroceryItem().getId());
            orderItem.setGroceryItem(item);
            double itemTotal = item.getPrice() * orderItem.getQuantity();
            orderItem.setItemTotal(itemTotal);
            totalPrice += itemTotal;
        }
        
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        return orderRepository.save(order);
    }
    
    public Order updateOrder(String id, Order orderDetails) {
        Order order = getOrderById(id);
        
        // Update customer if provided
        if (orderDetails.getCustomer() != null && orderDetails.getCustomer().getId() != null) {
            Customer customer = customerService.getCustomerById(orderDetails.getCustomer().getId());
            order.setCustomer(customer);
        }
        
        // Update order items and recalculate total
        if (orderDetails.getOrderItems() != null) {
            double totalPrice = 0.0;
            for (Order.OrderItem orderItem : orderDetails.getOrderItems()) {
                GroceryItem item = groceryItemService.getGroceryItemById(orderItem.getGroceryItem().getId());
                orderItem.setGroceryItem(item);
                double itemTotal = item.getPrice() * orderItem.getQuantity();
                orderItem.setItemTotal(itemTotal);
                totalPrice += itemTotal;
            }
            order.setOrderItems(orderDetails.getOrderItems());
            order.setTotalPrice(totalPrice);
        }
        
        return orderRepository.save(order);
    }
    
    public void deleteOrder(String id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }
}