package com.webservice.bookstore.exception;

import org.springframework.validation.FieldError;

public class MatchUserPasswordException extends ValidationException {

    public MatchUserPasswordException() {
    }

    public MatchUserPasswordException(String message, FieldError fieldError) {
        super(message, fieldError);
    }

    public MatchUserPasswordException(String message, Throwable cause, FieldError fieldError) {
        super(message, cause, fieldError);
    }
}
