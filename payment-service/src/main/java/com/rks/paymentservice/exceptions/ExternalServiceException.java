package com.rks.paymentservice.exceptions;

public class ExternalServiceException extends BaseException {

  private static final long serialVersionUID = 9100820119704439950L;

  public ExternalServiceException(String status, int code, String message) {
    super(status, code, message);
  }

  public ExternalServiceException(String message, String status, int code,
                                  String message1) {
    super(message, status, code, message1);
  }

  public ExternalServiceException(String message, Throwable cause, String status, int code,
                                  String message1) {
    super(message, cause, status, code, message1);
  }
}