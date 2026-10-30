package com.tele.telefood.controller;

import com.tele.telefood.dto.ReceiveUserInfo;
import com.tele.telefood.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/info")
    public ResponseEntity<?> getCustomerInfo(@RequestHeader("UserId") Integer id){
        try {
            ReceiveUserInfo customerInfo = customerService.getCustomerInfo(id);
            return ResponseEntity.ok().body(customerInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }
}
