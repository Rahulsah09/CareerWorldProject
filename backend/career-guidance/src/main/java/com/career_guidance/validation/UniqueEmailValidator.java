package com.career_guidance.validation;

import com.career_guidance.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Validator implementation for UniqueEmail annotation
 */
@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    
    private final UserRepository userRepository;
    
    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        // No initialization required
    }
    
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            return true; // Let @NotBlank handle empty validation
        }
        
        return !userRepository.existsByEmail(email);
    }
}