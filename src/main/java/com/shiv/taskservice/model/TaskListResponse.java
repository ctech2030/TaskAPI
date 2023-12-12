package com.shiv.taskservice.model;

import java.io.Serializable;
import java.util.List;

public class TaskListResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Task> tasks;
	private int totalPages;
	private int currentPage;
	private int pageSize;

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
