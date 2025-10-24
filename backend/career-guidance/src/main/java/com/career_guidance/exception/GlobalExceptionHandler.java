package com.career_guidance.exception;

import com.career_guidance.dto.response.ApiResponse;
import com.career_guidance.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for consistent error responses across the application
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle business exceptions
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(
            BusinessException ex, WebRequest request) {
        
        log.error("Business exception occurred: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(ex.getHttpStatus().value())
                .error(ex.getHttpStatus().getReasonPhrase())
                .message(ex.getMessage())
                .code(ex.getErrorCode())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ApiResponse.error(ex.getMessage(), errorResponse));
    }

    /**
     * Handle validation errors from @Valid annotation
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        log.error("Validation error occurred: {}", ex.getMessage());
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .message("Input validation failed")
                .code("VALIDATION_ERROR")
                .path(request.getDescription(false).replace("uri=", ""))
                .validationErrors(errors)
                .build();
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Input validation failed", errorResponse));
    }

    /**
     * Handle constraint violation exceptions
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {
        
        log.error("Constraint violation occurred: {}", ex.getMessage());
        
        Map<String, String> errors = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                    violation -> violation.getPropertyPath().toString(),
                    ConstraintViolation::getMessage
                ));
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Constraint Violation")
                .message("Constraint validation failed")
                .code("CONSTRAINT_VIOLATION")
                .path(request.getDescription(false).replace("uri=", ""))
                .validationErrors(errors)
                .build();
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Constraint validation failed", errorResponse));
    }

    /**
     * Handle authentication exceptions
     */
    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(
            Exception ex, WebRequest request) {
        
        log.error("Authentication error occurred: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Authentication Failed")
                .message("Invalid credentials")
                .code("AUTHENTICATION_ERROR")
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Authentication failed", errorResponse));
    }

    /**
     * Handle access denied exceptions
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {
        
        log.error("Access denied: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .error("Access Denied")
                .message("Insufficient permissions to access this resource")
                .code("ACCESS_DENIED")
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("Access denied", errorResponse));
    }

    /**
     * Handle data integrity violations (duplicate entries, etc.)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {
        
        log.error("Data integrity violation: {}", ex.getMessage());
        
        String message = "Data integrity violation";
        if (ex.getMessage() != null) {
            if (ex.getMessage().contains("Duplicate entry")) {
                message = "Duplicate entry - this record already exists";
            } else if (ex.getMessage().contains("cannot be null")) {
                message = "Required field cannot be null";
            }
        }
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Data Integrity Violation")
                .message(message)
                .code("DATA_INTEGRITY_VIOLATION")
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(message, errorResponse));
    }

    /**
     * Handle method argument type mismatch
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        
        log.error("Method argument type mismatch: {}", ex.getMessage());
        
        String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s", 
                ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Invalid Parameter")
                .message(message)
                .code("INVALID_PARAMETER")
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(message, errorResponse));
    }

    /**
     * Handle malformed JSON requests
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, WebRequest request) {
        
        log.error("Malformed JSON request: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Malformed JSON")
                .message("Invalid JSON format in request body")
                .code("MALFORMED_JSON")
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Invalid JSON format", errorResponse));
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(
            Exception ex, WebRequest request) {
        
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("An unexpected error occurred")
                .code("INTERNAL_SERVER_ERROR")
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("An unexpected error occurred", errorResponse));
    }
}