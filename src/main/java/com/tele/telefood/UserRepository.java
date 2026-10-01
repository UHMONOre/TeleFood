package com.tele.telefood;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);

    List<User> findByLoginTimeBeforeAndLoggedInFlagTrue(LocalDateTime oneHourAgo);
}
