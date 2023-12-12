package com.shiv.taskservice.TaskService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.shiv.taskservice.model.Task;
import com.shiv.taskservice.repository.TaskRepository;
import com.shiv.taskservice.service.TaskService;

@SpringBootTest
public class TaskServiceTest {

	@Mock
	private TaskRepository taskRepository;

	@InjectMocks
	private TaskService taskService;

	@Test
	void getAllTasks() {
		List<Task> tasks = new ArrayList<>();
		tasks.add(new Task());
		tasks.add(new Task());

		Mockito.when(taskRepository.findAll()).thenReturn(tasks);

		List<Task> result = taskService.getAllTasks();

		assertEquals(2, result.size());
	}

	@Test
	void getTaskById() {
		Long taskId = 1L;
		Task task = new Task();
		task.setId(taskId);

		Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

		Optional<Task> result = taskService.getTaskById(taskId);

		assertTrue(result.isPresent());
		assertEquals(taskId, result.get().getId());
	}

	@Test
	void createTask() {
		Task task = new Task();
		task.setTitle("Test Task");

		Mockito.when(taskRepository.save(task)).thenReturn(task);

		Task result = taskService.createTask(task);

		assertEquals("Test Task", result.getTitle());
	}

	@Test
	void updateTask() {
		Long taskId = 1L;
		Task existingTask = new Task();
		existingTask.setId(taskId);
		existingTask.setTitle("Existing Task");

		Task updatedTask = new Task();
		updatedTask.setTitle("Updated Task");

		Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
		Mockito.when(taskRepository.save(updatedTask)).thenReturn(updatedTask);

		Task result = taskService.updateTask(taskId, updatedTask);

		assertEquals("Updated Task", result.getTitle());
	}

	@Test
	void deleteTask() {
		Long taskId = 1L;
		taskService.deleteTask(taskId);
		Mockito.verify(taskRepository, Mockito.times(1)).deleteById(taskId);
	}

	@Test
	void getOverdueTasks() {
		// Mock data
		// Mocking the TaskService behavior
		List<Task> mockedTasks = List.of(new Task(1L, "Overdue Task 1", "Description 1", "pending", "high", null, null),
				new Task(2L, "Overdue Task 2", "Description 2", "in progress", "medium", null, null));

		// Mocking behavior of the taskRepository
		when(taskRepository.findOverdueTasks(any(LocalDate.class))).thenReturn(mockedTasks);

		// Call the actual method you want to test
		List<Task> result = taskService.getOverdueTasks();

		// Verify the result
		assertEquals(2, result.size());
		assertEquals("Overdue Task 1", result.get(0).getTitle());
		assertEquals("Overdue Task 2", result.get(1).getTitle());

	}

}
