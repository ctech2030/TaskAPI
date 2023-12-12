package com.shiv.taskservice.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shiv.taskservice.model.Task;

/**
 * JpaRepository is an interface provided by Spring Data JPA that extends the
 * PagingAndSortingRepository. It provides CRUD (Create, Read, Update, Delete)
 * operations out of the box and includes methods for common database
 * operations. By extending JpaRepository in your own interface or class and
 * specifying the entity type and the type of the entity's primary key, you
 * inherit basic database operations without having to write the boilerplate
 * code.
 * 
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

	/**
	 * Method to retrieve tasks that are overdue based on the due date
	 * 
	 * @param currentDate
	 * @return
	 */
	@Query("SELECT t FROM Task t WHERE t.dueDate < :currentDate")
	List<Task> findOverdueTasks(@Param("currentDate") LocalDate currentDate);

}
