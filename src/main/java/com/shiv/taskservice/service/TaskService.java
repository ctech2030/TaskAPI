package com.shiv.taskservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shiv.taskservice.controller.TaskCacheConfig;
import com.shiv.taskservice.model.Task;
import com.shiv.taskservice.repository.TaskRepository;

/**
 * The service layer is responsible for implementing business logic and
 * coordinating tasks between the presentation layer and the data access layer
 * (repositories).
 */
@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public Page<Task> getAllTasks(Pageable pageable) {
		return taskRepository.findAll(pageable);
	}

	/**
	 * Retrieve task details based on the task ID.
	 * 
	 * @param taskId
	 * @return
	 */
	@Cacheable(TaskCacheConfig.TASK_CACHE)
	public Optional<Task> getTaskById(Long taskId) {
		return taskRepository.findById(taskId);
	}

	/**
	 * Creating new task basis on request
	 * 
	 * @param task
	 * @return
	 */
	public Task createTask(Task task) {
		return taskRepository.save(task);
	}

	/**
	 * Update task details based on the task ID.
	 * 
	 * @param taskId
	 * @param updatedTask
	 * @return
	 */
	@CacheEvict(TaskCacheConfig.TASK_CACHE)
	public Task updateTask(Long taskId, Task updatedTask) {
		Optional<Task> existingTask = taskRepository.findById(taskId);
		if (existingTask.isPresent()) {
			updatedTask.setId(taskId);
			return taskRepository.save(updatedTask);
		}
		return null; // Handle not found scenario
	}

	/**
	 * delete task details based on the task ID.
	 * 
	 * @param taskId
	 */
	@CacheEvict(TaskCacheConfig.TASK_CACHE)
	public void deleteTask(Long taskId) {
		taskRepository.deleteById(taskId);
	}

	/**
	 * Getting all task
	 * 
	 * @return
	 */
	@CacheEvict(TaskCacheConfig.TASK_CACHE)
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	/**
	 * Method to retrieve tasks that are overdue based on the due date
	 * 
	 * @return
	 */
	public List<Task> getOverdueTasks() {
		LocalDate currentDate = LocalDate.now();
		return taskRepository.findOverdueTasks(currentDate);
	}
}
