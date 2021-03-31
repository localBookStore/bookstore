package com.webservice.bookstore.exception;

import org.springframework.validation.FieldError;

import java.util.List;

public class NotEnoughStockException extends ValidationException {

    public NotEnoughStockException() {
    }

    public NotEnoughStockException(String message, FieldError fieldError) {
        super(message, fieldError);
    }

    public NotEnoughStockException(String message, Throwable cause, FieldError fieldError) {
        super(message, cause, fieldError);
    }

    public NotEnoughStockException(String message, List<FieldError> errors) {
        super(message, errors);
    }

    public NotEnoughStockException(String message, Throwable cause, List<FieldError> errors) {
        super(message, cause, errors);
    }

}
