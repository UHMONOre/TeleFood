package com.tele.telefood.controller;

import com.tele.telefood.dto.OrderItemRequest;
import com.tele.telefood.dto.ReceiveOrderInfo;
import com.tele.telefood.dto.ReceiveSingleOrderInfo;
import com.tele.telefood.dto.ReceiveUserInfo;
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

    @GetMapping("/all/info")
    public ResponseEntity<?> getOrderInfo(@RequestHeader("UserId") Integer vendorId) {
        try {
            ReceiveOrderInfo orderInfo = orderService.getOrderInfo(vendorId);
            return ResponseEntity.ok().body(orderInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @GetMapping("/{orderId}/info")
    public ResponseEntity<?> getSingleOrderInfo(@RequestHeader("UserId") Integer vendorId, @PathVariable("orderId") Integer orderId) {
        try {
            ReceiveSingleOrderInfo orderInfo = orderService.getSingleOrderInfo(vendorId, orderId);
            return ResponseEntity.ok().body(orderInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestHeader("UserId") Integer customerId, @RequestBody List<OrderItemRequest> requestedItems) {
        try {
            orderService.createOrder(customerId, requestedItems);
            return ResponseEntity.status(200).body("Order successfully created.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PostMapping("/{orderId}/cancel")
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
    public ResponseEntity<String> rejectOrder(@RequestHeader("UserId") Integer courierId, @PathVariable Integer orderId) {
        try {
            orderService.processCourierRejection(orderId, courierId);
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

    @PostMapping("/{orderId}/preparing")
    public ResponseEntity<String> orderStatusPreparing(@RequestHeader("UserId") Integer vendorId, @PathVariable Integer orderId) {
        try {
            orderService.updateStatusPreparing(orderId, vendorId);
            return ResponseEntity.status(200).body("Order status changed to preparing.");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PostMapping("/{orderId}/delivering")
    public ResponseEntity<String> orderStatusDelivering(@RequestHeader("UserId") Integer vendorId, @PathVariable Integer orderId) {
        try {
            orderService.updateStatusDelivering(orderId, vendorId);
            return ResponseEntity.status(200).body("Order status changed to delivering.");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PostMapping("/{orderId}/vendor/completed")
    public ResponseEntity<String> orderStatusCompletedVendor(@RequestHeader("UserId") Integer vendorId, @PathVariable Integer orderId) {
        try {
            orderService.updateStatusCompleted(orderId, vendorId);
            return ResponseEntity.status(200).body("Order status changed to completed by vendor.");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PostMapping("/{orderId}/courier/completed")
    public ResponseEntity<String> orderStatusCompletedCourier(@RequestHeader("UserId") Integer courierId, @PathVariable Integer orderId) {
        try {
            orderService.updateStatusCompletedByCourier(orderId, courierId);
            return ResponseEntity.status(200).body("Order status changed to completed by courier.");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @GetMapping("/{orderId}/customer/info")
    public ResponseEntity<?> getCustomerInfo(@RequestHeader("UserId") Integer vendorId, @PathVariable Integer orderId) {
        try {
            ReceiveUserInfo customerInfo = orderService.getCustomerInfo(vendorId, orderId);
            return ResponseEntity.status(200).body(customerInfo);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }
}
