package com.lifequest.backend.controller;

import com.lifequest.backend.model.task;
import com.lifequest.backend.model.user;
import com.lifequest.backend.service.gameService;
import com.lifequest.backend.service.taskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class taskController {

    private final taskService taskService;
    private final gameService gameService;


    public taskController(
            taskService taskService,
            gameService gameService) {

        this.taskService = taskService;
        this.gameService = gameService;
    }


    // ========================================
    // CREATE TASK
    // ========================================

    @PostMapping("/{userId}")
    public ResponseEntity<?> createTask(
            @PathVariable Long userId,
            @RequestBody task newTask) {

        try {

            task createdTask =
                    taskService.createTask(
                            userId,
                            newTask
                    );

            return ResponseEntity.ok(
                    createdTask
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // ========================================
    // GET USER TASKS
    // ========================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserTasks(
            @PathVariable Long userId) {

        try {

            List<task> tasks =
                    taskService.getUserTasks(
                            userId
                    );

            return ResponseEntity.ok(tasks);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // ========================================
    // GET SINGLE TASK
    // ========================================

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(
            @PathVariable Long taskId) {

        try {

            task existingTask =
                    taskService.getTask(
                            taskId
                    );

            return ResponseEntity.ok(
                    existingTask
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // ========================================
    // UPDATE TASK
    // ========================================

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(
            @PathVariable Long taskId,
            @RequestBody task updatedTask) {

        try {

            task updated =
                    taskService.updateTask(
                            taskId,
                            updatedTask
                    );

            return ResponseEntity.ok(updated);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // ========================================
    // DELETE TASK
    // ========================================

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(
            @PathVariable Long taskId) {

        try {

            taskService.deleteTask(taskId);

            return ResponseEntity.ok(
                    "Task deleted successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // ========================================
    // COMPLETE QUEST
    // ========================================

    @PostMapping("/{taskId}/complete")
    public ResponseEntity<?> completeTask(
            @PathVariable Long taskId) {

        try {

            user player =
                    gameService.completeTask(
                            taskId
                    );


            /*
             * Do NOT return the complete user object.
             * This prevents the password hash from
             * being sent to the frontend.
             */

            Map<String, Object> response =
                    new HashMap<>();


            response.put(
                    "id",
                    player.getId()
            );

            response.put(
                    "username",
                    player.getUsername()
            );

            response.put(
                    "email",
                    player.getEmail()
            );

            response.put(
                    "level",
                    player.getLevel()
            );

            response.put(
                    "xp",
                    player.getXp()
            );

            response.put(
                    "gold",
                    player.getGold()
            );

            response.put(
                    "strength",
                    player.getStrength()
            );

            response.put(
                    "intellect",
                    player.getIntellect()
            );

            response.put(
                    "discipline",
                    player.getDiscipline()
            );

            response.put(
                    "streak",
                    player.getStreak()
            );


            return ResponseEntity.ok(
                    response
            );


        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}