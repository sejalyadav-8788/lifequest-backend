package com.lifequest.backend.controller;

import com.lifequest.backend.dto.authResponse;
import com.lifequest.backend.dto.loginRequest;
import com.lifequest.backend.model.user;
import com.lifequest.backend.service.userService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class authController {

    private final userService userService;

    public authController(userService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody signupRequest request) {

        try {

            user newUser = userService.registerUser(
                    request.username,
                    request.email,
                    request.password
            );

            return ResponseEntity.ok(
                    createResponse(newUser)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody loginRequest request) {

        try {

            user loggedInUser = userService.loginUser(
                    request.username,
                    request.password
            );

            return ResponseEntity.ok(
                    createResponse(loggedInUser)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    private authResponse createResponse(user currentUser) {

        return new authResponse(
                currentUser.getId(),
                currentUser.getUsername(),
                currentUser.getEmail(),
                currentUser.getLevel(),
                currentUser.getXp(),
                currentUser.getGold(),
                currentUser.getStrength(),
                currentUser.getIntellect(),
                currentUser.getDiscipline(),
                currentUser.getStreak()
        );
    }

    public static class signupRequest {

        public String username;
        public String email;
        public String password;
    }
}