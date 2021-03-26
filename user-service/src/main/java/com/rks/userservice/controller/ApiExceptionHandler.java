package com.rks.userservice.controller;

import com.rks.mcommon.error.ApiError;
import com.rks.mcommon.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static com.rks.userservice.constants.ErrorCodeConstants.INVALID_CREDENTIALS_ERR_CODE;
import static com.rks.userservice.constants.ErrorMessageConstants.INVALID_CREDENTIALS_ERR_MSG;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> handleConstraintViolation(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());

        e.getConstraintViolations().forEach(constraintViolation -> {
            errors.add(constraintViolation.getPropertyPath() + " : "+ constraintViolation.getMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException e) {
        ApiError error = new ApiError(INVALID_CREDENTIALS_ERR_CODE, INVALID_CREDENTIALS_ERR_MSG);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException nfe) {
        ApiError error = new ApiError(nfe.getCode(), nfe.getCustomMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiError> defaultExceptionHandler(Exception e) {
//        ApiError error = new ApiError(API_RESPONSE_STATUS_FAILURE, API_RESPONSE_STATUS_FAILURE_CODE, e.getMessage());
//        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
//    }

