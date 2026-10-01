package com.tele.telefood;

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
    public void deleteUser(@RequestHeader("UserId") Integer userId,  @PathVariable("userid") Integer deletedUserId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getId().equals(deletedUserId)) {
            userRepository.delete(user);
        }else if(user.getId() == 1 || user.getAdminFlag()){
            User deletedUser = userRepository.findById(deletedUserId).orElseThrow(() -> new IllegalArgumentException("Invalid Id"));

            userRepository.delete(deletedUser);
        }else {
            throw new IllegalArgumentException("invalid authorization");
        }
    }
}
