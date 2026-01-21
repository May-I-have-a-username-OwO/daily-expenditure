package com.aid.daily_expenditure;

import com.aid.daily_expenditure.Security.MyValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleControllerValidationExceptions(
            MethodArgumentNotValidException exception
    ) {
        Map<String, List<String>> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.computeIfAbsent(error.getField(), key -> new ArrayList<>())
                    .add(error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MyValidationException.class)
    public  ResponseEntity<Map<String, String>> handleServiceValidationExceptions(
            MyValidationException exception
    ) {
        Map<String, String> errors = new HashMap<>();
        exception.getErrors().forEach(error ->
                errors.put(error.getObjectName(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}
