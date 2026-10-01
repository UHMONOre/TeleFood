package com.tele.telefood;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Integer processLogin(@RequestBody LoginRequest loginRequest) throws Exception {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new Exception("User not found"));

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new Exception("Wrong password");
        }

        user.setLoggedInFlag(true);
        user.setLoginTime(LocalDateTime.now());

        userRepository.save(user);

        return user.getId();
    }

    @Transactional
    public Integer processRegister(@RequestBody LoginRequest loginRequest) throws Exception {
        if (userRepository.findByEmail(loginRequest.getEmail()).isPresent()) {
            throw new Exception("User already exists");
        }

        User user = new User(loginRequest.getEmail(), loginRequest.getPassword(), loginRequest.getFirstName(), loginRequest.getLastName());
        userRepository.save(user);

        return user.getId();
    }
}
