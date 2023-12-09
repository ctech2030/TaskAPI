CREATE DATABASE taskapi;

USE taskapi;;

-- Create Project table
CREATE TABLE Project (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Create Task table
CREATE TABLE Task (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL CHECK (status IN ('pending', 'in progress', 'completed')),
    priority VARCHAR(20) NOT NULL,
    due_date DATE,
    project_id INTEGER REFERENCES Project(id) ON DELETE CASCADE
);

-- Sample data for testing
INSERT INTO Project (name, description) VALUES
    ('Project A', 'Description for Project A'),
    ('Project B', 'Description for Project B');

INSERT INTO Task (title, description, status, priority, due_date, project_id) VALUES
    ('Task 1', 'Description for Task 1', 'pending', 'high', '2023-12-31', 1),
    ('Task 2', 'Description for Task 2', 'in progress', 'medium', '2023-12-15', 1),
    ('Task 3', 'Description for Task 3', 'completed', 'low', '2023-12-20', 2);