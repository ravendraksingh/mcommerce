package com.rks.orderservice.exception;


public class DaoLayerException extends BaseException {

  private static final long serialVersionUID = 8481520701774085099L;

  public DaoLayerException(String status, int code, String message) {
    super(status, code, message);
  }

  public DaoLayerException(String message, String status, int code, String message1) {
    super(message, status, code, message1);
  }

  public DaoLayerException(String message, Throwable cause, String status, int code,
                           String message1) {
    super(message, cause, status, code, message1);
  }
}
