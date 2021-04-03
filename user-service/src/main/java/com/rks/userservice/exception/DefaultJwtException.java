package com.rks.userservice.exception;

public class DefaultJwtException extends BaseException {
    private static final long serialVersionUID = -1057807218758703057L;

    public DefaultJwtException(String status, int code, String customMessage) {
        super(status, code, customMessage);
    }

    public DefaultJwtException(String message, String status, int code, String customMessage) {
        super(message, status, code, customMessage);
    }

    public DefaultJwtException(String message, Throwable cause, String status, int code, String customMessage) {
        super(message, cause, status, code, customMessage);
    }
}
