package com.rks.userservice.exception;


public class InvalidParametersException extends BaseException {

  private static final long serialVersionUID = 2732487784038782466L;

  public InvalidParametersException(String status, int code, String message) {
    super(status, code, message);
  }

  public InvalidParametersException(String message, String status, int code,
                                    String message1) {
    super(message, status, code, message1);
  }

  public InvalidParametersException(String message, Throwable cause, String status, int code,
                                    String message1) {
    super(message, cause, status, code, message1);
  }
}