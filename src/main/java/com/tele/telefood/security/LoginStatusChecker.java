package com.tele.telefood.security;

import com.tele.telefood.entity.User;
import com.tele.telefood.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

public class LoginStatusChecker {
    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedDelay = 300000)
    @Transactional
    public void checkLoginStatus() {
        LocalDateTime oneHourAgo = LocalDateTime.now();

        List<User> loggedInUsers = userRepository.findByLoginTimeBeforeAndLoggedInFlagTrue(oneHourAgo);

        if (!loggedInUsers.isEmpty()) {
            for (User user : loggedInUsers) {
                user.setLoggedInFlag(false);
            }

            userRepository.saveAll(loggedInUsers);
        }
    }
}
