package com.tele.telefood;

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

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
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
}
