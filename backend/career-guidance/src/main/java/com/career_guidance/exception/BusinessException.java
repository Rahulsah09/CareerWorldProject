package com.career_guidance.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base exception class for all business logic exceptions
 */
@Getter
public abstract class BusinessException extends RuntimeException {
    
    private final String errorCode;
    private final HttpStatus httpStatus;
    
    protected BusinessException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
    
    protected BusinessException(String message, String errorCode, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}