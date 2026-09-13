package com.lifequest.backend.repository;

import com.lifequest.backend.model.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepository extends JpaRepository<user, Long> {

    Optional<user> findByUsername(String username);

    Optional<user> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}