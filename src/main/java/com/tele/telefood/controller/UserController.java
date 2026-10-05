package com.tele.telefood.controller;

import com.tele.telefood.dto.UpdateEmailRequest;
import com.tele.telefood.dto.UpdateNameRequest;
import com.tele.telefood.dto.UpdatePasswordRequest;
import com.tele.telefood.entity.User;
import com.tele.telefood.repository.UserRepository;
import com.tele.telefood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/delete/{userid}")
    public ResponseEntity<String> deleteUser(@RequestHeader("UserId") Integer userId, @PathVariable("userid") Integer deletedUserId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getId().equals(deletedUserId)) {
            userRepository.delete(user);
            return ResponseEntity.ok("Your account has been successfully deleted.");
        }else if(user.getId().equals(1) || user.getAdminFlag()){
            User deletedUser = userRepository.findById(deletedUserId).orElseThrow(() -> new IllegalArgumentException("Invalid Id"));

            userRepository.delete(deletedUser);

            return ResponseEntity.ok("Admin successfully deleted user with ID: " + deletedUserId);
        }else {
            throw new IllegalArgumentException("invalid authorization");
        }
    }

    @PutMapping("/update/name")
    public ResponseEntity<String> updateName(@RequestHeader("UserId") Integer userId, @RequestBody UpdateNameRequest  request) {
        try {
            userService.updateName(userId, request.getFirstName(), request.getLastName());
            return ResponseEntity.ok("Your name has been successfully updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PutMapping("/update/email")
    public ResponseEntity<String> updateEmail(@RequestHeader("UserId") Integer userId, @RequestBody UpdateEmailRequest request) {
        try {
            userService.updateEmail(userId, request.getEmail());
            return ResponseEntity.ok("Your email has been successfully updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }

    @PutMapping("/update/password")
    public ResponseEntity<String> updatePassword(@RequestHeader("UserId") Integer userId, @RequestBody UpdatePasswordRequest request) {
        try {
            userService.updatePassword(userId, request.getPassword());
            return ResponseEntity.ok("Your password has been successfully updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error occurred.");
        }
    }
}
