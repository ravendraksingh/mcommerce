package com.rks.paymentservice.exceptions;


public class RequestConflictException extends BaseException {

  private static final long serialVersionUID = -3420915283919089464L;

  public RequestConflictException(String status, int code, String message) {
    super(status, code, message);
  }

  public RequestConflictException(String message, String status, int code, String message1) {
    super(message, status, code, message1);
  }

  public RequestConflictException(String message, Throwable cause, String status, int code,
                                  String message1) {
    super(message, cause, status, code, message1);
  }
}
