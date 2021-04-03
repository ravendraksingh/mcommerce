package com.rks.userservice.exception;

public class UnauthorizedAccessException extends BaseException {

  private static final long serialVersionUID = 4759109979990971743L;

  public UnauthorizedAccessException(String status, int code, String message) {
    super(status, code, message);
  }

  public UnauthorizedAccessException(String message, String status, int code,
                                     String message1) {
    super(message, status, code, message1);
  }

  public UnauthorizedAccessException(String message, Throwable cause, String status, int code,
                                     String message1) {
    super(message, cause, status, code, message1);
  }
}