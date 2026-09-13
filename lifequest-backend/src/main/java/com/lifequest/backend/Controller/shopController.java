package com.lifequest.backend.controller;

import com.lifequest.backend.model.user;
import com.lifequest.backend.repository.userRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop")
@CrossOrigin(origins = "*")
public class shopController {

    private final userRepository userRepository;

    public shopController(userRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/{userId}/buy")
    public ResponseEntity<?> buyItem(
            @PathVariable Long userId,
            @RequestParam String item,
            @RequestParam int price) {

        try {

            user player = userRepository.findById(userId)
                    .orElseThrow(() ->
                            new RuntimeException("User not found"));

            if (price < 0) {
                return ResponseEntity.badRequest()
                        .body("Invalid price");
            }

            if (player.getGold() < price) {
                return ResponseEntity.badRequest()
                        .body("Not enough Gold");
            }

            player.setGold(
                    player.getGold() - price
            );

            userRepository.save(player);

            return ResponseEntity.ok(
                    item + " purchased successfully!"
            );

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
}