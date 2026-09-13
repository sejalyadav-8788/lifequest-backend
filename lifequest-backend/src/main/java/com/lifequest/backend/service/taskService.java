package com.lifequest.backend.service;

import com.lifequest.backend.model.task;
import com.lifequest.backend.model.user;
import com.lifequest.backend.repository.taskRepository;
import com.lifequest.backend.repository.userRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class taskService {

    private final taskRepository taskRepository;
    private final userRepository userRepository;

    public taskService(taskRepository taskRepository,
                       userRepository userRepository) {

        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public task createTask(Long userId, task newTask) {

        user existingUser = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        newTask.setUser(existingUser);

        return taskRepository.save(newTask);
    }

    public List<task> getUserTasks(Long userId) {

        return taskRepository.findByUserId(userId);
    }

    public task getTask(Long taskId) {

        return taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));
    }

    public task updateTask(
            Long taskId,
            task updatedTask) {

        task existingTask = getTask(taskId);

        existingTask.setTitle(
                updatedTask.getTitle()
        );

        existingTask.setDescription(
                updatedTask.getDescription()
        );

        existingTask.setCategory(
                updatedTask.getCategory()
        );

        existingTask.setXpReward(
                updatedTask.getXpReward()
        );

        existingTask.setGoldReward(
                updatedTask.getGoldReward()
        );

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long taskId) {

        if (!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Task not found");
        }

        taskRepository.deleteById(taskId);
    }
}