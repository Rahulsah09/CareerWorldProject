package com.career_guidance.validation;

import com.career_guidance.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Validator implementation for UniqueUsername annotation
 */
@Component
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    
    private final UserRepository userRepository;
    
    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        // No initialization required
    }
    
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.trim().isEmpty()) {
            return true; // Let @NotBlank handle empty validation
        }
        
        return !userRepository.existsByUsername(username);
    }
}