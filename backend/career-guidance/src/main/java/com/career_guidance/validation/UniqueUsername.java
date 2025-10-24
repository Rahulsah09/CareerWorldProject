package com.career_guidance.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation to ensure username is unique
 */
@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    
    String message() default "Username is already taken";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}