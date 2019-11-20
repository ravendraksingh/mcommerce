package com.rks.orderservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private String status;

    private int code;

    private String customMessage;

    public BaseException(String status, int code, String customMessage) {
        this.status = status;
        this.code = code;
        this.customMessage = customMessage;
    }

    public BaseException(String message, String status, int code, String customMessage) {
        super(message);
        this.status = status;
        this.code = code;
        this.customMessage = customMessage;
    }

    public BaseException(String message, Throwable cause, String status, int code,
                         String customMessage) {
        super(message, cause);
        this.status = status;
        this.code = code;
        this.customMessage = customMessage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(" | Exception : " + getClass());
        if(getCause() != null)
            sb.append(" | Cause : " + getCause());
        if(getMessage() != null)
            sb.append(" | Exception message : " + getMessage().replaceAll("\"", ""));
        if(getStatus() != null)
            sb.append(" | Status : " + getStatus());
        sb.append(" | Code : " + this.getCode());
        if(getCustomMessage() != null)
            sb.append(" | Custom message : " + getCustomMessage());
        if(getStackTrace() != null)
            sb.append(" | StackTrace : " + StringUtils.join(
                    Arrays.asList(getStackTrace()).subList(0, Math.min(getStackTrace().length,15)),
                    "____"));
        return sb.toString();
    }
}
