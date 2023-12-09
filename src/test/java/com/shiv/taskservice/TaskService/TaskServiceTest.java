package com.shiv.taskservice.TaskService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.shiv.taskservice.model.Task;
import com.shiv.taskservice.repository.TaskRepository;
import com.shiv.taskservice.service.TaskService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
