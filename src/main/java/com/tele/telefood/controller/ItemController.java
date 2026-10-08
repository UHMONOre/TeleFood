package com.tele.telefood.controller;

import com.tele.telefood.dto.CreateItemRequest;
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
}
