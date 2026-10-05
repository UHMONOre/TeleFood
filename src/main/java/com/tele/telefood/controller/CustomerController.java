package com.tele.telefood.controller;

import com.tele.telefood.dto.UpdateLocationRequest;
import com.tele.telefood.dto.UpdatePhoneNumberRequest;
import com.tele.telefood.entity.Customer;
import com.tele.telefood.repository.CustomerRepository;
import com.tele.telefood.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @PutMapping("/update/country")
    public ResponseEntity<String> updateCountry(@RequestHeader("UserId") Integer userId, @RequestBody UpdateLocationRequest updateLocationRequest) {
        try {
            customerService.updateCountry(userId, updateLocationRequest);
            return ResponseEntity.ok("Your location has been successfully updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PutMapping("/update/city")
    public ResponseEntity<String> updateCity(@RequestHeader("UserId") Integer userId, @RequestBody UpdateLocationRequest updateLocationRequest) {
        try {
            customerService.updateCity(userId, updateLocationRequest);
            return ResponseEntity.ok("Your location has been successfully updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PutMapping("/update/address")
    public ResponseEntity<String> updateAddress(@RequestHeader("UserId" ) Integer userId, @RequestBody UpdateLocationRequest updateLocationRequest) {
        try {
            customerService.updateAddress(userId, updateLocationRequest);
            return ResponseEntity.ok("Your location has been successfully updated.");
        }  catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PutMapping("/update/phone")
    public ResponseEntity<String> updatePhoneNumber(@RequestHeader("UserId") Integer userId, @RequestBody UpdatePhoneNumberRequest  updatePhoneNumberRequest) {
        try {
            customerService.updatePhone(userId, updatePhoneNumberRequest.getPhoneNumber());
            return ResponseEntity.ok("Your phone number has been successfully updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }
}
