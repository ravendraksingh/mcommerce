package com.rks.catalog.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
    String status;
    int code;
    String message;
}

