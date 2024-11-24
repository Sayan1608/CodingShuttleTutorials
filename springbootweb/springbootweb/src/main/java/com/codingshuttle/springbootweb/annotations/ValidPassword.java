package com.codingshuttle.springbootweb.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
    String message() default "Password is not Valid, must be atleast 10 characters long, have atleast " +
            "one Upper, lower and Special Case Character";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
