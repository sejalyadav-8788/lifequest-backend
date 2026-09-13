package com.lifequest.backend.repository;

import com.lifequest.backend.model.task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface taskRepository extends JpaRepository<task, Long> {

    List<task> findByUserId(Long userId);
}