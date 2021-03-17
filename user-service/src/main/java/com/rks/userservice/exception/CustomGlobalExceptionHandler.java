package com.rks.userservice.exception;

import com.rks.userservice.errors.FieldErrorObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(ConstraintViolationException.class)
//    public void handleConstraintViolationException(HttpServletResponse response) throws IOException {
//        response.sendError(HttpStatus.BAD_REQUEST.value());
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        //return super.handleMethodArgumentNotValid(ex, headers, status, request);
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", dateFormat.format(new Date()));
        body.put("status", status.value());

        List<FieldErrorObject> errors = new ArrayList<>();
        //get all fields errors
        for(FieldError err : ex.getBindingResult().getFieldErrors()) {
            FieldErrorObject feo = new FieldErrorObject(Integer.valueOf(err.getCode()),
                    err.getDefaultMessage(), err.getField());
            errors.add(feo);
        }
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }
}
