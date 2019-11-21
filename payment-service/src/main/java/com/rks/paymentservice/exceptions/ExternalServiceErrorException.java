package com.rks.paymentservice.exceptions;


public class ExternalServiceErrorException extends BaseException {

  private static final long serialVersionUID = 1816263599337711015L;

  public ExternalServiceErrorException(String status, int code, String message) {
    super(status, code, message);
  }

  public ExternalServiceErrorException(String message, String status, int code, String message1) {
    super(message, status, code, message1);
  }

  public ExternalServiceErrorException(String message, Throwable cause, String status, int code,
                                       String message1) {
    super(message, cause, status, code, message1);
  }
}
