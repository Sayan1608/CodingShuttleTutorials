package com.codingshuttle.springbootweb.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmployeeEmailValidator.class)
public @interface EmailNotValid {
    String message() default "Emails must ends with @coding-shuttle.com";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
