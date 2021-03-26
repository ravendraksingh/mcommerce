package com.rks.userservice.exception;

import com.rks.mcommon.exception.BaseException;

public class UserAlreadyExistException extends BaseException {

    private static final long serialVersionUID = 7407976242747911392L;

    public UserAlreadyExistException(String status, int code, String message) {
        super(status, code, message);
    }

    public UserAlreadyExistException(String message, String status, int code, String message1) {
        super(message, status, code, message1);
    }

    public UserAlreadyExistException(String message, Throwable cause, String status, int code,
                             String message1) {
        super(message, cause, status, code, message1);
    }
}
