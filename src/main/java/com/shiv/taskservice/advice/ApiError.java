package com.shiv.taskservice.advice;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;

/**
 * ApiError is a custom error response class that encapsulates error details
 * such as timestamp, message, HTTP status, and any additional errors.
 * 
 * @author Shivpal Chouhan
 */
public class ApiError {
    private LocalDateTime timestamp;
    private String message;
    private HttpStatus status;
    private Map<String, String> errors;

    public ApiError(String message, HttpStatus status) {
	this.timestamp = LocalDateTime.now();
	this.message = message;
	this.status = status;
    }

    public ApiError(String message, HttpStatus status, Map<String, String> errors) {
	this(message, status);
	this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
	this.timestamp = timestamp;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public HttpStatus getStatus() {
	return status;
    }

    public void setStatus(HttpStatus status) {
	this.status = status;
    }

    public Map<String, String> getErrors() {
	return errors;
    }

    public void setErrors(Map<String, String> errors) {
	this.errors = errors;
    }

}
