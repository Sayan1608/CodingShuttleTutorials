package com.codingshuttle.springbootweb.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrimeNumberValidator implements ConstraintValidator<ValidPrimeNumber,Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return isPrimeNumber(value);
    }

    private boolean isPrimeNumber(Integer num){
        for(int i=2;i<=Math.sqrt(num);i++){
            if(num%i==0) return false;
        }
        return true;
    }
}
