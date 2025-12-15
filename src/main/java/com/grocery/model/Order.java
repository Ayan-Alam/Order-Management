package com.grocery.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {
    
    @Id
    private String id;
    
    @NotNull(message = "Customer is required")
    @DBRef
    private Customer customer;
    
    @NotNull(message = "Grocery items are required")
    private List<OrderItem> orderItems;
    
    @NotNull(message = "Order date is required")
    private LocalDateTime orderDate;
    
    @NotNull(message = "Total price is required")
    private Double totalPrice;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem {
        @DBRef
        private GroceryItem groceryItem;
        private Integer quantity;
        private Double itemTotal;
    }
}