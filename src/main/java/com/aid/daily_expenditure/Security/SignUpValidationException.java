package com.aid.daily_expenditure.Security;

import com.aid.daily_expenditure.User.SignUpInput;
import org.springframework.validation.ObjectError;

import java.util.List;

public class SignUpValidationException extends RuntimeException{
    private final List<ObjectError> errors;
    private final SignUpInput input;

    public SignUpValidationException(List<ObjectError> errors, SignUpInput input) {
        super("SignUpValidationException");
        this.errors = errors;
        this.input = input;
    }


    public List<ObjectError> getErrors() {
        return errors;
    }

    public SignUpInput getInput() {
        return input;
    }
}
