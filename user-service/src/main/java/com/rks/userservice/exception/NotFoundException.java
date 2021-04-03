package com.rks.userservice.exception;

public class NotFoundException extends BaseException {

    private static final long serialVersionUID = -1057807218758703057L;

    public NotFoundException(String status, int code, String message) {
        super(status, code, message);
    }

    public NotFoundException(String message, String status, int code, String message1) {
        super(message, status, code, message1);
    }

    public NotFoundException(String message, Throwable cause, String status, int code,
                             String message1) {
        super(message, cause, status, code, message1);
    }

}
