package com.lifequest.backend.service;

import com.lifequest.backend.model.user;
import com.lifequest.backend.repository.userRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userService {

    private final userRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public userService(userRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public user registerUser(String username, String email, String password) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        user newUser = new user();

        newUser.setUsername(username);
        newUser.setEmail(email);

        // Encrypt password before saving it
        newUser.setPassword(passwordEncoder.encode(password));

        return userRepository.save(newUser);
    }
    public user loginUser(String username, String password) {

        user existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return existingUser;
    }
}