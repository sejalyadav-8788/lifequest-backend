package com.lifequest.backend.service;

import com.lifequest.backend.model.task;
import com.lifequest.backend.model.user;
import com.lifequest.backend.repository.taskRepository;
import com.lifequest.backend.repository.userRepository;
import org.springframework.stereotype.Service;

@Service
public class gameService {

    private final taskRepository taskRepository;
    private final userRepository userRepository;

    public gameService(taskRepository taskRepository,
                       userRepository userRepository) {

        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    public user completeTask(Long taskId) {

        task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));


        if (existingTask.isCompleted()) {

            throw new RuntimeException(
                    "Task is already completed"
            );
        }


        user player = existingTask.getUser();


        // ========================================
        // MARK TASK COMPLETED
        // ========================================

        existingTask.setCompleted(true);


        // ========================================
        // ADD XP
        // ========================================

        int newXp =
                player.getXp()
                        + existingTask.getXpReward();

        player.setXp(newXp);


        // ========================================
        // ADD GOLD
        // ========================================

        int newGold =
                player.getGold()
                        + existingTask.getGoldReward();

        player.setGold(newGold);


        // ========================================
        // INCREASE STREAK
        // ========================================

        player.setStreak(
                player.getStreak() + 1
        );


        // ========================================
        // INCREASE ATTRIBUTE
        // ========================================

        increaseAttribute(
                player,
                existingTask.getCategory()
        );


        // ========================================
        // CHECK LEVEL UP
        // ========================================

        checkLevelUp(player);


        // ========================================
        // SAVE
        // ========================================

        userRepository.save(player);

        taskRepository.save(existingTask);


        return player;
    }


    // ========================================
    // ATTRIBUTE SYSTEM
    // ========================================

    private void increaseAttribute(
            user player,
            String category) {

        if (category == null) {
            return;
        }


        switch (category.toUpperCase()) {

            case "FITNESS":

                player.setStrength(
                        player.getStrength() + 1
                );

                break;


            case "STUDY":

                player.setIntellect(
                        player.getIntellect() + 1
                );

                break;


            case "DISCIPLINE":

                player.setDiscipline(
                        player.getDiscipline() + 1
                );

                break;


            default:

                break;
        }
    }


    // ========================================
    // LEVEL SYSTEM
    // ========================================

    private void checkLevelUp(user player) {

        int requiredXp =
                player.getLevel() * 100;


        while (player.getXp() >= requiredXp) {

            player.setXp(
                    player.getXp() - requiredXp
            );


            player.setLevel(
                    player.getLevel() + 1
            );


            requiredXp =
                    player.getLevel() * 100;
        }
    }
}