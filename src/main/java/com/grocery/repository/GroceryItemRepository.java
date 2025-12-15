package com.grocery.repository;

import com.grocery.model.GroceryItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroceryItemRepository extends MongoRepository<GroceryItem, String> {
    List<GroceryItem> findByCategory(String category);
    List<GroceryItem> findByNameContainingIgnoreCase(String name);
}