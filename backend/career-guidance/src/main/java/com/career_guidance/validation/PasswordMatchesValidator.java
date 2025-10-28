package com.career_guidance.validation;

import com.career_guidance.dto.SignupRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for PasswordMatches annotation
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        // No initialization required
    }
    
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) {
            return true; // Let @NotNull handle null validation
        }
        
        if (obj instanceof SignupRequest) {
            SignupRequest signupRequest = (SignupRequest) obj;
            String password = signupRequest.getPassword();
            String confirmPassword = signupRequest.getConfirmPassword();
            
            if (password == null || confirmPassword == null) {
                return true; // Let @NotNull handle null validation
            }
            
            boolean isValid = password.equals(confirmPassword);
            
            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Passwords do not match")
                        .addPropertyNode("confirmPassword")
                        .addConstraintViolation();
            }
            
            return isValid;
        }
        
        return true;
    }
}