package com.codingshuttle.springbootweb.annotations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeEmailValidator implements ConstraintValidator<EmailNotValid, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.endsWith("@coding-shuttle.com");
    }
}
