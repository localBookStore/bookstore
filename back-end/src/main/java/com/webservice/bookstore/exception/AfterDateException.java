package com.webservice.bookstore.exception;

public class AfterDateException extends RuntimeException{

    public AfterDateException(String message) {
        super(message);
    }
}
