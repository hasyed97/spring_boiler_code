package com.spring.boilercode.customValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotNullFieldValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNullField {
    String message() default "Field cannot be null or empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}