package com.shiv.taskservice.advice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Handle @Valid on @RequestBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		ApiError apiError = new ApiError("Validation failed", HttpStatus.BAD_REQUEST, errors);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	// Handle @Valid/@Validated on path params or query params
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations()
				.forEach(violation -> errors.put(violation.getPropertyPath().toString(), violation.getMessage()));
		ApiError apiError = new ApiError("Constraint violation", HttpStatus.BAD_REQUEST, errors);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	// Handle multiple common exceptions (like IllegalArgumentException, etc.)
	@ExceptionHandler({ IllegalArgumentException.class, NullPointerException.class })
	public ResponseEntity<ApiError> handleCommonExceptions(Exception ex) {
		ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	// Catch-all fallback
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleAllExceptions(Exception ex) {
		ApiError apiError = new ApiError("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static class ApiError {
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
}
