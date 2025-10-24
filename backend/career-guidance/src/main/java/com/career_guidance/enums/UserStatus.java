package com.career_guidance.enums;

/**
 * Enumeration for user account status
 */
public enum UserStatus {
    /**
     * User account is active and can be used normally
     */
    ACTIVE,
    
    /**
     * User account is temporarily suspended
     */
    SUSPENDED,
    
    /**
     * User account is locked (usually due to failed login attempts)
     */
    LOCKED,
    
    /**
     * User account is inactive/disabled
     */
    INACTIVE,
    
    /**
     * User account has expired
     */
    EXPIRED,
    
    /**
     * User credentials have expired and need to be updated
     */
    CREDENTIALS_EXPIRED,
    
    /**
     * User account is pending activation/verification
     */
    PENDING_ACTIVATION,
    
    /**
     * User account has been permanently banned
     */
    BANNED
}