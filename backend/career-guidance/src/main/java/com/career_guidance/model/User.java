package com.career_guidance.model;

import com.career_guidance.enums.UserRole;
import com.career_guidance.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * User entity representing a system user with comprehensive profile information
 * Implements UserDetails for Spring Security integration
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_username", columnList = "username"),
    @Index(name = "idx_email", columnList = "email"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    
    @Column(name = "first_name", length = 50)
    private String firstName;
    
    @Column(name = "last_name", length = 50)
    private String lastName;
    
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false, length = 20)
    private UserRole role = UserRole.USER;
    
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false, length = 20)
    private UserStatus status = UserStatus.ACTIVE;
    
    @Builder.Default
    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;
    
    @Column(name = "email_verification_token")
    private String emailVerificationToken;
    
    @Column(name = "password_reset_token")
    private String passwordResetToken;
    
    @Column(name = "password_reset_expires_at")
    private LocalDateTime passwordResetExpiresAt;
    
    @Builder.Default
    @Column(name = "failed_login_attempts", nullable = false)
    private Integer failedLoginAttempts = 0;
    
    @Column(name = "account_locked_until")
    private LocalDateTime accountLockedUntil;
    
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    
    @Column(name = "last_password_change_at")
    private LocalDateTime lastPasswordChangeAt;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Version
    private Long version;
    
    // Additional fields for career guidance
    @Column(length = 1000)
    private String skills;
    
    @Column(length = 1000)
    private String interests;
    
    // UserDetails implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return status != UserStatus.EXPIRED;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return status != UserStatus.LOCKED && 
               (accountLockedUntil == null || accountLockedUntil.isBefore(LocalDateTime.now()));
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return status != UserStatus.CREDENTIALS_EXPIRED;
    }
    
    @Override
    public boolean isEnabled() {
        return status == UserStatus.ACTIVE;
    }
    
    // Utility methods
    public String getFullName() {
        if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        } else if (firstName != null) {
            return firstName;
        } else if (lastName != null) {
            return lastName;
        }
        return username;
    }
    
    public boolean isAccountLocked() {
        return accountLockedUntil != null && accountLockedUntil.isAfter(LocalDateTime.now());
    }
    
    public void lockAccount(int minutes) {
        this.accountLockedUntil = LocalDateTime.now().plusMinutes(minutes);
        this.status = UserStatus.LOCKED;
    }
    
    public void unlockAccount() {
        this.accountLockedUntil = null;
        this.failedLoginAttempts = 0;
        if (this.status == UserStatus.LOCKED) {
            this.status = UserStatus.ACTIVE;
        }
    }
    
    public void incrementFailedLoginAttempts() {
        this.failedLoginAttempts++;
    }
    
    public void resetFailedLoginAttempts() {
        this.failedLoginAttempts = 0;
    }
}