package com.aid.daily_expenditure;

import org.springframework.validation.ObjectError;

import java.util.List;

public class MyValidationException extends RuntimeException{
    private final List<ObjectError> errors;

    public MyValidationException(List<ObjectError> errors) {
        super("MyValidationException");
        this.errors = errors;
    }


    public List<ObjectError> getErrors() {
        return errors;
    }
}
