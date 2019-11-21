package com.rks.paymentservice.exceptions;

public class MicroServiceUnavailableException extends BaseException {

  private static final long serialVersionUID = 2115283169069846939L;

  public MicroServiceUnavailableException(String status, int code, String message) {
    super(status, code, message);
  }

  public MicroServiceUnavailableException(String message, String status, int code,
                                          String message1) {
    super(message, status, code, message1);
  }

  public MicroServiceUnavailableException(String message, Throwable cause, String status, int code,
                                          String message1) {
    super(message, cause, status, code, message1);
  }
}