package com.rks.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ApiSuccessResponse {
    private String status;
    private String message;
    private Map<String, Object> data;

    public ApiSuccessResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
    public ApiSuccessResponse(String status, String message, Map<String, Object> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
