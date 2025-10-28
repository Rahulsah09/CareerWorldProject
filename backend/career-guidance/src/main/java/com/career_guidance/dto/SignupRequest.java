package com.career_guidance.dto;

import com.career_guidance.validation.PasswordMatches;
import com.career_guidance.validation.UniqueEmail;
import com.career_guidance.validation.UniqueUsername;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user registration request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
@Schema(description = "User registration request")
public class SignupRequest {
    
    @Schema(description = "Username (3-50 characters, alphanumeric and underscore only)", 
            example = "john_doe123")
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", 
             message = "Username can only contain letters, numbers and underscores")
    @UniqueUsername
    private String username;
    
    @Schema(description = "Email address", example = "john.doe@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    @UniqueEmail
    private String email;
    
    @Schema(description = "Password (min 8 chars, must contain uppercase, lowercase, and number)", 
            example = "MySecurePassword123")
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&].*$", 
             message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    private String password;
    
    @Schema(description = "Password confirmation", example = "MySecurePassword123")
    @NotBlank(message = "Please confirm your password")
    @JsonProperty("confirmPassword")
    private String confirmPassword;
    
    @Schema(description = "First name", example = "John")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "First name can only contain letters and spaces")
    private String firstName;
    
    @Schema(description = "Last name", example = "Doe")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Last name can only contain letters and spaces")
    private String lastName;
    
    @Schema(description = "Phone number", example = "+1234567890")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Please provide a valid phone number")
    private String phoneNumber;
    
    @Schema(description = "Date of birth", example = "1990-01-01")
    @Past(message = "Date of birth must be in the past")
    private java.time.LocalDate dateOfBirth;
    
    @Schema(description = "Accept terms and conditions", example = "true")
    @AssertTrue(message = "You must accept the terms and conditions")
    private Boolean acceptTerms;
}
