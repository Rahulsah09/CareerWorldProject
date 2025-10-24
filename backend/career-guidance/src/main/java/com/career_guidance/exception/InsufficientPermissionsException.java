package com.career_guidance.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown for insufficient permissions
 */
public class InsufficientPermissionsException extends BusinessException {
    
    public InsufficientPermissionsException(String message) {
        super(message, "INSUFFICIENT_PERMISSIONS", HttpStatus.FORBIDDEN);
    }
}