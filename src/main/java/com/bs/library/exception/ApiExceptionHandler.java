package com.bs.library.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    protected ResponseEntity<Object> handleUsernameNotFoundException(Exception ex, WebRequest request) {
        String message = "Username not found";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {UserAlreadyExists.class})
    protected ResponseEntity<Object> handleUserAlreadyExist(Exception ex, WebRequest request) {
        String message = "User is already exists!";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {OrderProcessedException.class})
    protected ResponseEntity<Object> handleProcessedException(Exception ex, WebRequest request) {
        String message = "Cannot proceed order!";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {BookOutOfStockException.class})
    protected ResponseEntity<Object> handleBookOutOfStockException(Exception ex, WebRequest request) {
        String message = "Product is out of stock!";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {UserAccountBalanceException.class})
    protected ResponseEntity<Object> handleUserAccountBalanceException(Exception ex, WebRequest request) {
        String message = "Check your account balance!";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    protected ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
        String message = "User not found!";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }



}
