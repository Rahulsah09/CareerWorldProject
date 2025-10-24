package com.career_guidance.exception;

import org.springframework.http.HttpStatus;

/**
 * Base class for user-related exceptions
 */
public class UserException extends BusinessException {
    
    public UserException(String message, String errorCode, HttpStatus status) {
        super(message, errorCode, status);
    }
    
    public UserException(String message) {
        super(message, "USER_ERROR", HttpStatus.BAD_REQUEST);
    }
}

/**
 * Exception thrown when user already exists
 */
class DuplicateUserException extends BusinessException {
    
    public DuplicateUserException(String message) {
        super(message, "USER_ALREADY_EXISTS", HttpStatus.CONFLICT);
    }
    
    public DuplicateUserException(String field, String value) {
        super(field + " '" + value + "' is already taken", "USER_ALREADY_EXISTS", HttpStatus.CONFLICT);
    }
}

/**
 * Exception thrown for invalid user operations
 */
class InvalidUserOperationException extends BusinessException {
    
    public InvalidUserOperationException(String message) {
        super(message, "INVALID_USER_OPERATION", HttpStatus.BAD_REQUEST);
    }
}

/**
 * Exception thrown when user account is locked
 */
class UserAccountLockedException extends BusinessException {
    
    public UserAccountLockedException(String message) {
        super(message, "USER_ACCOUNT_LOCKED", HttpStatus.LOCKED);
    }
    
    public UserAccountLockedException(String username, int lockoutDuration) {
        super("Account locked for user: " + username + ". Try again in " + lockoutDuration + " minutes", 
              "USER_ACCOUNT_LOCKED", HttpStatus.LOCKED);
    }
}