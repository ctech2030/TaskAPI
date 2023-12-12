package com.shiv.taskservice.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.shiv.taskservice.model.Task;
import com.shiv.taskservice.model.TaskListResponse;
import com.shiv.taskservice.service.TaskService;

/**
 * 
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@GetMapping()
	public TaskListResponse getTasks(@RequestParam(defaultValue = "priority") String sort,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {

		// Create Pageable for sorting and pagination
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sort));

		// Get the Page of tasks
		Page<Task> taskPage = taskService.getAllTasks(pageable);

		// Convert Page to List
		List<Task> tasks = taskPage.getContent();

		// Create the response
		TaskListResponse response = new TaskListResponse();
		response.setTasks(tasks);
		response.setTotalPages(taskPage.getTotalPages());
		response.setCurrentPage(taskPage.getNumber() + 1); // Page numbers start from 0
		response.setPageSize(taskPage.getSize());

		return response;
	}

	/**
	 * The "/task/{task-id}" Get API is responsible Retrieve task details based on
	 * the task ID.
	 * 
	 * @param taskId
	 * @return
	 */
	@GetMapping("/{taskId}")
	public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
		Optional<Task> task = taskService.getTaskById(taskId);
		return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<Task> createTask(@RequestBody Task task) {
		Task createdTask = taskService.createTask(task);
		return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
	}

	/**
	 * The /task/{task-id} Put API is responsible for update a task based on its ID
	 * and request body
	 * 
	 * @param taskId
	 * @param updatedTask
	 * @return
	 */
	@PutMapping("/{taskId}")
	public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
		Task task = taskService.updateTask(taskId, updatedTask);
		if (task != null) {
			return new ResponseEntity<>(task, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * The /task/{task-id} Delete API is responsible for removing a task based on
	 * its ID.
	 * 
	 * @param taskId
	 * @return
	 */
	@DeleteMapping("/{taskId}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
		taskService.deleteTask(taskId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Method to retrieve tasks that are overdue based on the due date
	 * 
	 * @return
	 */
	@GetMapping("/overdue")
	public List<Task> getOverdueTasks() {
		return taskService.getOverdueTasks();
	}
}
