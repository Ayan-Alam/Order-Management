package com.grocery.service;

import com.grocery.exception.ResourceNotFoundException;
import com.grocery.model.GroceryItem;
import com.grocery.repository.GroceryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroceryItemService {
    
    private final GroceryItemRepository groceryItemRepository;
    
    public List<GroceryItem> getAllGroceryItems() {
        return groceryItemRepository.findAll();
    }
    
    public GroceryItem getGroceryItemById(String id) {
        return groceryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grocery item not found with id: " + id));
    }
    
    public List<GroceryItem> getGroceryItemsByCategory(String category) {
        return groceryItemRepository.findByCategory(category);
    }
    
    public GroceryItem createGroceryItem(GroceryItem groceryItem) {
        return groceryItemRepository.save(groceryItem);
    }
    
    public GroceryItem updateGroceryItem(String id, GroceryItem itemDetails) {
        GroceryItem groceryItem = getGroceryItemById(id);
        groceryItem.setName(itemDetails.getName());
        groceryItem.setCategory(itemDetails.getCategory());
        groceryItem.setPrice(itemDetails.getPrice());
        groceryItem.setQuantity(itemDetails.getQuantity());
        return groceryItemRepository.save(groceryItem);
    }
    
    public void deleteGroceryItem(String id) {
        GroceryItem groceryItem = getGroceryItemById(id);
        groceryItemRepository.delete(groceryItem);
    }
}