package com.tele.telefood.controller;

import com.tele.telefood.dto.UpdateCourierRequest;
import com.tele.telefood.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courier")
public class CourierController {
    @Autowired
    private CourierService courierService;

    @PutMapping("/update/tin")
    public ResponseEntity<String> updateTin(@RequestHeader("UserId") Integer id, @RequestBody UpdateCourierRequest request) {
        try {
            courierService.updateTin(id, request.getTin());
            return ResponseEntity.ok("Tin updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PutMapping("/update/availability")
    public ResponseEntity<String> updateAvailability(@RequestHeader("UserId") Integer id, @RequestBody UpdateCourierRequest request) {
        try {
            courierService.updateAvailability(id, request.getAvailability());
            return ResponseEntity.ok("Availability updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }
}
