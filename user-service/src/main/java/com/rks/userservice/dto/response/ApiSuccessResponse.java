package com.rks.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import springfox.documentation.spring.web.json.Json;

import java.util.Map;

@Data
public class ApiSuccessResponse {
    private String status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
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
