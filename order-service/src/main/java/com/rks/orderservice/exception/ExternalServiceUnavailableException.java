package com.rks.orderservice.exception;

public class ExternalServiceUnavailableException extends BaseException {

  private static final long serialVersionUID = -8353874658078785813L;

  public ExternalServiceUnavailableException(String status, int code, String message) {
    super(status, code, message);
  }

  public ExternalServiceUnavailableException(String message, String status, int code,
                                             String message1) {
    super(message, status, code, message1);
  }

  public ExternalServiceUnavailableException(String message, Throwable cause, String status,
                                             int code,
                                             String message1) {
    super(message, cause, status, code, message1);
  }
}