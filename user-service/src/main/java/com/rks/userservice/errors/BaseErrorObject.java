package com.rks.userservice.errors;

import java.io.Serializable;

public class BaseErrorObject implements Serializable {
    private int code;
    private String message;

    public BaseErrorObject(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
