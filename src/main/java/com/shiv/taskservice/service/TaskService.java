package com.shiv.taskservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shiv.taskservice.model.Task;
import com.shiv.taskservice.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
 
	private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Task updatedTask) {
        Optional<Task> existingTask = taskRepository.findById(taskId);
        if (existingTask.isPresent()) {
            updatedTask.setId(taskId);
            return taskRepository.save(updatedTask);
        }
        return null; // Handle not found scenario
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

    // Additional methods for business logic
}
