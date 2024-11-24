package com.codingshuttle.springbootweb.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       return isValidPassword(value);
    }

    private boolean isValidPassword(String password){
        if(password.length() < 10) return false;
        // Check for at least one uppercase character
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);

        // Check for at least one lowercase character
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);

        // Check for at least one special character
        boolean hasSpecialCharacter = password.chars().anyMatch(ch -> "!@#$%^&*()_+-=[]{}|;:'\",.<>?/`~".indexOf(ch) >= 0);

        // Return true only if all conditions are met
        return hasUpperCase && hasLowerCase && hasSpecialCharacter;
    }
}
