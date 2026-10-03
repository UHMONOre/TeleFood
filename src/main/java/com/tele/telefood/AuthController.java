package com.tele.telefood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        try {
            Integer userId = userService.processLogin(loginRequest);

            return ResponseEntity.ok("{\"userId\": " + userId + " is successfully logged in.}");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @PostMapping("/register/customer")
    public ResponseEntity<String> registerCustomer(@RequestBody RegisterRequestCustomer registerRequest) {

        try {
            Integer customerId = userService.processRegister(registerRequest);
            return ResponseEntity.ok("{\"userId\": " + customerId + " is successfully registered}");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("User already exists");
        }
    }

    @PostMapping("/register/vendor")
    public ResponseEntity<String> registerVendor(@RequestBody RegisterRequestVendor registerRequest) {

        try {
            Integer vendorId = userService.processRegister(registerRequest);
            return ResponseEntity.ok("{\"userId\": " + vendorId + " is successfully registered}");
        } catch (Exception e){
            return ResponseEntity.status(401).body("User already exists");
        }
    }
}
