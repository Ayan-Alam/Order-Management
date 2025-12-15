package com.grocery.controller;

import com.grocery.model.GroceryItem;
import com.grocery.service.GroceryItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/grocery-items")
@RequiredArgsConstructor
public class GroceryItemController {
    
    private final GroceryItemService groceryItemService;
    
    @GetMapping
    public ResponseEntity<List<GroceryItem>> getAllGroceryItems() {
        return ResponseEntity.ok(groceryItemService.getAllGroceryItems());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GroceryItem> getGroceryItemById(@PathVariable String id) {
        return ResponseEntity.ok(groceryItemService.getGroceryItemById(id));
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<GroceryItem>> getGroceryItemsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(groceryItemService.getGroceryItemsByCategory(category));
    }
    
    @PostMapping
    public ResponseEntity<GroceryItem> createGroceryItem(@Valid @RequestBody GroceryItem groceryItem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groceryItemService.createGroceryItem(groceryItem));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<GroceryItem> updateGroceryItem(@PathVariable String id, @Valid @RequestBody GroceryItem groceryItem) {
        return ResponseEntity.ok(groceryItemService.updateGroceryItem(id, groceryItem));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroceryItem(@PathVariable String id) {
        groceryItemService.deleteGroceryItem(id);
        return ResponseEntity.noContent().build();
    }
}