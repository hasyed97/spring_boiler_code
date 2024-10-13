package com.spring.boilercode.customValidator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotNullFieldValidator implements ConstraintValidator<NotNullField, String> {

    @Override
    public void initialize(NotNullField constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null;
    }
}
