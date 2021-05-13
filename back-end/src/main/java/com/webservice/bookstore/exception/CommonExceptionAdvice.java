package com.webservice.bookstore.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.AuthenticationFailedException;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.List;

@Log4j2
@ControllerAdvice
@RestController
public class CommonExceptionAdvice  {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ValidationException.class})
    public ErrorResponse validationExceptionHandler(ValidationException e) {
        log.error(e.getMessage(), e);
        List<FieldError> errors = e.getErrors();
        return new ErrorResponse(400, e.getMessage(), errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class, AfterDateException.class})
    public ErrorResponse badRequestHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(400, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {AuthenticationFailedException.class, UnauthorizedException.class})
    public ErrorResponse unauthorizedHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(401, e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {NullPointerException.class, FileNotFoundException.class, FileSystemException.class})
    public ErrorResponse notFoundHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(404, e.getMessage());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ErrorResponse methodNotAllowedHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(405, "지원하지 않는 API 입니다.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public ErrorResponse internalServerErrorHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(500, "Internal server error");
    }

    @Getter
    @NoArgsConstructor
    public static class ErrorResponse {
        private int code;
        private String message;
        private List<FieldError> errors;

        public ErrorResponse(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public ErrorResponse(int code, String message, List<FieldError> errors) {
            this.code = code;
            this.message = message;
            this.errors = errors;
        }
    }
}
