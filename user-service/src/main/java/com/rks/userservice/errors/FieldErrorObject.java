package com.rks.userservice.errors;

public class FieldErrorObject extends BaseErrorObject {
    private String field;

    public FieldErrorObject(int code, String message, String field) {
        super(code, message);
        this.field = field;
    }
}
