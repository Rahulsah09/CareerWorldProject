package com.career_guidance.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown for invalid credentials during authentication
 */
public class InvalidCredentialsException extends BusinessException {
    
    public InvalidCredentialsException(String message) {
        super(message, "INVALID_CREDENTIALS", HttpStatus.UNAUTHORIZED);
    }
    
    public InvalidCredentialsException() {
        super("Invalid username or password", "INVALID_CREDENTIALS", HttpStatus.UNAUTHORIZED);
    }
}