package com.webservice.bookstore.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ValidationException extends RuntimeException {
    private List<FieldError> errors = new ArrayList<>();

    public ValidationException(String message, FieldError fieldError) {
        super(message);
        this.errors.add(fieldError);
    }

    public ValidationException(String message, Throwable cause, FieldError fieldError) {
        super(message, cause);
        this.errors.add(fieldError);
    }

    public ValidationException(String message, List<FieldError> errors) {
        super(message);
        this.errors = errors;
    }

    public ValidationException(String message, Throwable cause, List<FieldError> errors) {
        super(message, cause);
        this.errors = errors;
    }
}
