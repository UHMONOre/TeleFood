package com.tele.telefood.controller;

import com.tele.telefood.dto.OrderItemRequest;
import com.tele.telefood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestHeader("UserId") Integer customerId, @RequestBody List<OrderItemRequest> requestedItems) {
        try {
            orderService.createOrder(customerId, requestedItems);
            return ResponseEntity.status(200).body("Order successfully created.");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PostMapping("{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@RequestHeader("UserId") Integer vendorId, @PathVariable Integer orderId) {
        try {
            orderService.cancelOrder(vendorId, orderId);
            return ResponseEntity.status(200).body("Order successfully cancelled.");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PostMapping("/{orderId}/accept")
    public ResponseEntity<String> acceptOrder(@RequestHeader("UserId") Integer courierId, @PathVariable Integer orderId) {
        try {
            orderService.processCourierAcceptation(orderId, courierId);
            return ResponseEntity.status(200).body("Order acceptance processed.");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PostMapping("/{orderId}/reject")
    public ResponseEntity<String> rejectOrder(@RequestHeader("UserId") Integer customerId, @PathVariable Integer orderId) {
        try {
            orderService.processCourierRejection(orderId, customerId);
            return ResponseEntity.status(200).body("Order rejection processed.");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }
}
