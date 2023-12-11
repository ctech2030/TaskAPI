package com.shiv.taskservice.TaskService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.shiv.taskservice.controller.TaskController;
import com.shiv.taskservice.model.Task;
import com.shiv.taskservice.model.TaskListResponse;
import com.shiv.taskservice.service.TaskService;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTasks() {
        MockitoAnnotations.openMocks(this);

        // Mocking the TaskService behavior
        List<Task> mockedTasks = List.of(
                new Task(1L, "Task 1", "Description 1", "pending", "high", null, null),
                new Task(2L, "Task 2", "Description 2", "in progress", "medium", null, null)
        );
        Page<Task> mockedTaskPage = mock(Page.class);
        when(mockedTaskPage.getContent()).thenReturn(mockedTasks);
        when(mockedTaskPage.getTotalPages()).thenReturn(2);
        when(mockedTaskPage.getNumber()).thenReturn(0);
        when(mockedTaskPage.getSize()).thenReturn(2);

        // Mocking the TaskService method
        when(taskService.getAllTasks(any(Pageable.class))).thenReturn(mockedTaskPage);

        // Call the controller method
        TaskListResponse response = taskController.getTasks("priority", 0, 10);

        // Verify that the TaskService method was called with the correct parameters
        verify(taskService, times(1)).getAllTasks(
                PageRequest.of(0, 10, Sort.by("priority"))
        );

        // Verify the response values
        assertEquals(mockedTasks, response.getTasks());
        assertEquals(2, response.getTotalPages());
        assertEquals(1, response.getCurrentPage());
        assertEquals(2, response.getPageSize());
    }

	@Test
	void getTaskById() {
		Long taskId = 1L;
		Task task = new Task();
		task.setId(taskId);

		when(taskService.getTaskById(taskId)).thenReturn(Optional.of(task));

		ResponseEntity<Task> result = taskController.getTaskById(taskId);

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(taskId, result.getBody().getId());
	}

    @Test
    void getTaskByIdNotFound() {
        Long taskId = 1L;

        when(taskService.getTaskById(taskId)).thenReturn(Optional.empty());

        ResponseEntity<Task> result = taskController.getTaskById(taskId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void createTask() {
        Task task = new Task();
        task.setTitle("Test Task");

        when(taskService.createTask(task)).thenReturn(task);

        ResponseEntity<Task> result = taskController.createTask(task);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Test Task", result.getBody().getTitle());
    }

    @Test
    void updateTask() {
        Long taskId = 1L;
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");

        when(taskService.updateTask(eq(taskId), any())).thenReturn(updatedTask);

        ResponseEntity<Task> result = taskController.updateTask(taskId, updatedTask);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Updated Task", result.getBody().getTitle());
    }

    @Test
    void updateTaskNotFound() {
        Long taskId = 1L;

        when(taskService.updateTask(eq(taskId), any())).thenReturn(null);

        ResponseEntity<Task> result = taskController.updateTask(taskId, new Task());

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteTask() {
        Long taskId = 1L;

        ResponseEntity<Void> result = taskController.deleteTask(taskId);

        verify(taskService, times(1)).deleteTask(taskId);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}
