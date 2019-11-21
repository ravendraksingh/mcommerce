package com.rks.paymentservice.exceptions;

public class MicroServiceErrorException extends BaseException {

  private static final long serialVersionUID = 1866039107787428787L;

  public MicroServiceErrorException(String status, int code, String message) {
    super(status, code, message);
  }

  public MicroServiceErrorException(String message, String status, int code, String message1) {
    super(message, status, code, message1);
  }

  public MicroServiceErrorException(String message, Throwable cause, String status, int code,
                                    String message1) {
    super(message, cause, status, code, message1);
  }
}
