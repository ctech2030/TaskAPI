Task Management System allows users to create, update, delete, and view tasks. Each task is associated
with a project and has attributes like title, description, status, priority, and deadlines.

Below is an outline of approach this task

```
1. REST API Endpoints:
Create a new task:
Route: POST /tasks
Method: POST
Request Structure: json

{
  "title": "Task Title",
  "description": "Task Description",
  "status": "pending",
  "priority": "high",
  "dueDate": "2023-12-31",
  "projectId": 1
}

Response Structure: json

{
  "id": 1,
  "title": "Task Title",
  "description": "Task Description",
  "status": "pending",
  "priority": "high",
  "dueDate": "2023-12-31",
  "projectId": 1
}
Update an existing task:
Route: PUT /tasks/{taskId}
Method: PUT
Request Structure: json

{
  "title": "Updated Task Title",
  "description": "Updated Task Description",
  "status": "in progress",
  "priority": "medium",
  "dueDate": "2023-12-31",
  "projectId": 1
}
Response Structure: json

{
  "id": 1,
  "title": "Updated Task Title",
  "description": "Updated Task Description",
  "status": "in progress",
  "priority": "medium",
  "dueDate": "2023-12-31",
  "projectId": 1
}

Delete a task:
Route: DELETE /tasks/{taskId}
Method: DELETE
Response: 204 No Content
Retrieve details of a specific task:
Route: GET /tasks/{taskId}
Method: GET
Response Structure: json

{
  "id": 1,
  "title": "Task Title",
  "description": "Task Description",
  "status": "pending",
  "priority": "high",
  "dueDate": "2023-12-31",
  "projectId": 1
}

List all tasks with sorting and pagination:
Route: GET /tasks
Method: GET
Query Parameters: sort (priority, deadline), page, pageSize
Response Structure: json

{
  "tasks": [
    {
      "id": 1,
      "title": "Task Title",
      "description": "Task Description",
      "status": "pending",
      "priority": "high",
      "dueDate": "2023-12-31",
      "projectId": 1
    },
    // ... other tasks
  ],
  "totalPages": 2,
  "currentPage": 1,
  "pageSize": 10
}

```

2. Database Design:
```
Task Table:
Columns: ID, Title, Description, Status, Priority, DueDate, ProjectId
Foreign Key: ProjectId references Project Table

Project Table:
Columns: ID, Name, Description
```
3. Business Logic:
Tasks can only be deleted or updated if they are in a pending or in-progress state.
Provide functionality to update task status.

4. Java Project Structure:
```
src/main/java/your/package/TaskController.java: REST API controllers.
src/main/java/your/package/TaskService.java: Business logic.
src/main/java/your/package/TaskRepository.java: Interface for database operations.
src/main/java/your/package/model/Task.java: Entity class for tasks.
src/main/java/your/package/model/Project.java: Entity class for projects.
src/test/java/your/package/TaskControllerTest.java: JUnit tests.
```
6. Testing:
Write JUnit tests covering all functionalities.

7. Code Quality and Documentation:
Use clean architecture principles.
Document API endpoints and provide clear code comments.
