package com.rks.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ApiError implements Serializable {
    //private final long serialVersionUID = -4555432630608839477L;

    private String status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> data;

    public ApiError(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiError(String status, String message, Map<String, Object> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
