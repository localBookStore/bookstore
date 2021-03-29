package com.webservice.bookstore.exception;

import org.springframework.validation.FieldError;

public class PreventRemembershipException extends ValidationException  {

    public PreventRemembershipException() {
    }

    public PreventRemembershipException(String message, FieldError fieldError) {
        super(message, fieldError);
    }

    public PreventRemembershipException(String message, Throwable cause, FieldError fieldError) {
        super(message, cause, fieldError);
    }
}
