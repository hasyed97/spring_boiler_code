package com.spring.boilercode.customValidator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyFieldValidator implements ConstraintValidator<NotEmptyField, String> {

    @Override
    public void initialize(NotEmptyField constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        // Check if the value is not empty
        return !value.trim().isEmpty();
    }
}
