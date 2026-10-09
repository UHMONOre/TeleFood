package com.tele.telefood.controller;

import com.tele.telefood.dto.CreateItemRequest;
import com.tele.telefood.dto.UpdateItemRequest;
import com.tele.telefood.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PutMapping("/create")
    public ResponseEntity<String> createItem(@RequestHeader("UserId") Integer userId, @RequestBody CreateItemRequest request) {
        try {
            itemService.createItem(userId, request);
            return ResponseEntity.ok("Item created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteItem(@RequestHeader("UserId") Integer userId, @RequestBody UpdateItemRequest request) {
        try {
            itemService.deleteItem(userId, request.getItemId());
            return ResponseEntity.ok("Item deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PutMapping("/update/name")
    public ResponseEntity<String> updateName(@RequestHeader("UserId") Integer userId, @RequestBody UpdateItemRequest request) {
        try {
            itemService.updateName(userId, request.getItemId(), request.getName());
            return ResponseEntity.ok("Item updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PutMapping("/update/description")
    public ResponseEntity<String> updateDescription(@RequestHeader("UserId") Integer userId, @RequestBody UpdateItemRequest request) {
        try {
            itemService.updateDescription(userId, request.getItemId(), request.getDescription());
            return ResponseEntity.ok("Item updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PutMapping("/update/price")
    public ResponseEntity<String> updatePrice(@RequestHeader("UserId") Integer userId, @RequestBody UpdateItemRequest request) {
        try {
            itemService.updatePrice(userId, request.getItemId(), request.getPrice());
            return ResponseEntity.ok("Item updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PutMapping("/update/category")
    public ResponseEntity<String> updateCategory(@RequestHeader("UserId") Integer userId, @RequestBody UpdateItemRequest request) {
        try {
            itemService.updateCategory(userId, request.getItemId(), request.getCategory());
            return ResponseEntity.ok("Item updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PutMapping("/update/discount")
    public ResponseEntity<String> updateDiscount(@RequestHeader("UserId") Integer userId, @RequestBody UpdateItemRequest request) {
        try {
            itemService.updateDiscount(userId, request.getItemId(), request.getDiscount());
            return ResponseEntity.ok("Item updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PutMapping("/update/stock")
    public ResponseEntity<String> updateStock(@RequestHeader("UserId") Integer userId, @RequestBody UpdateItemRequest request) {
        try {
            itemService.updateStock(userId, request.getItemId(), request.getStock());
            return ResponseEntity.ok("Item updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }
}
