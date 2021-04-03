package com.rks.userservice.exception;


import com.rks.userservice.dto.response.ErrorResponse;
import com.rks.userservice.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static com.rks.userservice.constants.UserServiceConstants.FAILED;
import static com.rks.userservice.constants.UserServiceErrorMessageConstants.INVALID_CREDENTIALS_ERR_MSG;
import static com.rks.userservice.constants.UserServiceErrorMessageConstants.INVALID_REQUEST_BODY_ERR_MSG;

@ControllerAdvice
public class RestExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> badRequestExceptionHandler(BadRequestException e) {
    return new ResponseEntity<>(this.getErrorObject(e), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity notFoundExceptionHandler(NotFoundException e) {
    return new ResponseEntity<>(this.getErrorObject(e), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UnauthorizedAccessException.class)
  public ResponseEntity<ErrorResponse> unauthorizedAccessExceptionHandler(
      UnauthorizedAccessException e) {
    return new ResponseEntity<>(this.getErrorObject(e), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(ExternalServiceUnavailableException.class)
  public ResponseEntity<ErrorResponse> externalServiceUnavailableExceptionHandler(
      ExternalServiceUnavailableException e) {
    return new ResponseEntity<>(this.getErrorObject(e), HttpStatus.OK);
  }

  @ExceptionHandler(MicroServiceUnavailableException.class)
  public ResponseEntity<ErrorResponse> microServiceUnavailableExceptionExceptionHandler(
      MicroServiceUnavailableException e) {
    return new ResponseEntity<>(this.getErrorObject(e), HttpStatus.OK);
  }

  @ExceptionHandler(PreValidationFailedException.class)
  public ResponseEntity<ErrorResponse> validationFailedExceptionHandler(
      PreValidationFailedException e) {
    return new ResponseEntity<>(this.getErrorObject(e), HttpStatus.OK);
  }

  @ExceptionHandler(ExternalServiceErrorException.class)
  public ResponseEntity<ErrorResponse> externalServiceErrorExceptionHandler(
      ExternalServiceErrorException e) {
    return new ResponseEntity<>(this.getErrorObject(e), HttpStatus.OK);
  }

  @ExceptionHandler(ExternalServiceException.class)
  public ResponseEntity<ErrorResponse> externalServiceExceptionHandler(
      ExternalServiceException e) {
    return new ResponseEntity<>(this.getErrorObject(e), HttpStatus.OK);
  }

  @ExceptionHandler(MicroServiceErrorException.class)
  public ResponseEntity<ErrorResponse> microServiceErrorExceptionHandler(
      MicroServiceErrorException e) {
    return new ResponseEntity<>(this.getErrorObject(e), HttpStatus.OK);
  }

  @ExceptionHandler(InvalidParametersException.class)
  public ResponseEntity<ErrorResponse> invalidParametersExceptionHandler(
      InvalidParametersException e) {
    ResponseEntity responseEntity = new ResponseEntity<>(this.getErrorObject(e), HttpStatus.OK);
    logger.error("Error Response: {}", responseEntity);
    return responseEntity;
  }

  @ExceptionHandler(DaoLayerException.class)
  public ResponseEntity<ErrorResponse> daoLayerExceptionExceptionHandler(
     DaoLayerException e) {
    return new ResponseEntity<>(this.getErrorObject(e), HttpStatus.OK);
  }

  @ExceptionHandler(RequestConflictException.class)
  public ResponseEntity<ErrorResponse> requestConflictExceptionHandler(
      RequestConflictException e) {
    return new ResponseEntity<>(this.getErrorObject(e), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> internalServerExceptionHandler(BaseException e) {
    return new ResponseEntity<>(this.getErrorObject(e), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /*@ExceptionHandler({ServletRequestBindingException.class,
      HttpMediaTypeNotSupportedException.class})
  public ResponseEntity<ErrorResponse> badRequestExceptionHandler(Exception e) {
    logger.error("Bad request: invalid header {}", CommonUtils.exceptionFormatter(e));
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(FAILED);
    errorResponse.setMessage(e.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }*/

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> badRequestExceptionHandler2(
      MethodArgumentNotValidException e) {
    logger.error("Bad request: invalid parameter {}",CommonUtils.exceptionFormatter(e));
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(FAILED);
    errorResponse.setMessage("Invalid parameter");
    BindingResult bindingResult = e.getBindingResult();
    if (bindingResult.hasErrors()) {
      for (ObjectError objectError : bindingResult.getAllErrors()) {
        errorResponse.setMessage(objectError.getDefaultMessage());
      }
    }
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> badRequestExceptionHandler3(
      MethodArgumentTypeMismatchException e) {
    logger.error("Bad request: invalid parameter {}",CommonUtils.exceptionFormatter(e));
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(FAILED);
    errorResponse.setMessage("Please provide " + e.getName() + " in correct format.");
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> constraintViolationException(
      ConstraintViolationException e) {
    logger.error("Bad request: invalid parameter {}",CommonUtils.exceptionFormatter(e));
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(FAILED);
    errorResponse.setMessage("Invalid parameter");
    for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
      errorResponse.setMessage(constraintViolation.getMessageTemplate());
    }
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> badRequestExceptionHandler(
      HttpMessageNotReadableException e) {
    logger.error("Bad request: invalid request body {}", CommonUtils.exceptionFormatter(e));
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(FAILED);
    errorResponse.setMessage(INVALID_REQUEST_BODY_ERR_MSG);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
    //logger.error("Bad request: invalid credentials.");
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(FAILED);
    errorResponse.setMessage("Access denied");
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

//  @ExceptionHandler(Exception.class)
//  public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
//    logger.error("Unhandled exception {}",CommonUtils.exceptionFormatter(e));
//    ErrorResponse errorResponse = new ErrorResponse();
//    errorResponse.setStatus(PENDING);
//    errorResponse.setMessage("Error occurred\n\nPlease try again in sometime");
//    logger.info("Status changed to pending {}", errorResponse);
//    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//  }


  @ExceptionHandler(AccessDeniedException.class)
  private ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
    logger.error("Access denied: {}", CommonUtils.exceptionFormatter(e));
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(FAILED);
    errorResponse.setMessage("Access denied");
    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  }

  private ErrorResponse getErrorObject(BaseException e) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(e.getStatus());
    errorResponse.setCode(e.getCode());
    errorResponse.setMessage(e.getCustomMessage());
    return errorResponse;
  }

}
