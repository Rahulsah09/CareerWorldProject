package com.career_guidance.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a user is not found
 */
public class UserNotFoundException extends BusinessException {
    
    public UserNotFoundException(String message) {
        super(message, "USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
    
    public UserNotFoundException(Long userId) {
        super("User not found with id: " + userId, "USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
    
    public UserNotFoundException() {
        super("User not found", "USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}