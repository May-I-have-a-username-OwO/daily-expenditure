package com.aid.daily_expenditure.Security;

import com.aid.daily_expenditure.User.SignUpInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleControllerValidationExceptions(
            MethodArgumentNotValidException exception,
            Model model
    ) {
        Map<String, List<String>> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
            errors.computeIfAbsent(error.getField(), a -> new ArrayList<>())    //return map value (list)
                    .add(error.getDefaultMessage())
        );

        model.addAttribute("showError", true);
        model.addAttribute("errors", errors);

        SignUpInput input = (SignUpInput) exception.getBindingResult().getTarget();
        if (input != null) {
            model.addAttribute("input", input);
            System.out.println('a');
        }

        return "signUp";
    }


    @ExceptionHandler(SignUpValidationException.class)
    public  String handleServiceValidationExceptions(
            SignUpValidationException exception,
            Model model
    ) {
        Map<String, List<String>> errors = new HashMap<>();
        exception.getErrors().forEach(error ->
                errors.computeIfAbsent(error.getObjectName(), a -> new ArrayList<>())    //return map value (list)
                    .add(error.getDefaultMessage())
        );

        model.addAttribute("showError", true);
        model.addAttribute("errors", errors);
        model.addAttribute("input", exception.getInput());

        return "signUp";
    }
}
