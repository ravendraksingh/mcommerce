package com.rks.orderservice.exception;

public class PreValidationFailedException extends BaseException {

  private static final long serialVersionUID = 7299026101410036846L;

  public PreValidationFailedException(String status, int code, String message) {
    super(status, code, message);
  }

  public PreValidationFailedException(String message, String status, int code,
                                      String message1) {
    super(message, status, code, message1);
  }

  public PreValidationFailedException(String message, Throwable cause, String status, int code,
                                      String message1) {
    super(message, cause, status, code, message1);
  }
}