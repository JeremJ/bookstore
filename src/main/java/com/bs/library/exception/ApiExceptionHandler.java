package com.bs.library.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {EmptyResultDataAccessException.class, EntityNotFoundException.class, BookNotFoundException.class})
    protected ResponseEntity<Object> handleEmptyResultDataAccess(Exception ex, WebRequest request) {
        String message = "Record does not exist";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    protected ResponseEntity<Object> handleNumberFormatException(Exception ex, WebRequest request) {
        String message = "Please type correct data format";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
