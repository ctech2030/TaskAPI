package com.shiv.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiv.taskservice.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Custom queries if needed
}
