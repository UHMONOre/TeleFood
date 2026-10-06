package com.tele.telefood.controller;

import com.tele.telefood.dto.UpdateTinRequest;
import com.tele.telefood.dto.UpdateVendorNameRequest;
import com.tele.telefood.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendor")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @PutMapping("/update/tin")
    public ResponseEntity<String> updateTin(@RequestHeader("UserId") Integer userId, @RequestBody UpdateTinRequest request) {
        try {
            vendorService.UpdateTin(userId, request.getTin());
            return ResponseEntity.status(200).body("Successfully updated tin.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PutMapping("/update/name")
    public ResponseEntity<String> updateName(@RequestHeader("UserId") Integer userId, @RequestBody UpdateVendorNameRequest request) {
        try {
            vendorService.UpdateName(userId, request.getVendorName());
            return ResponseEntity.status(200).body("Successfully updated vendor name.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }
}
