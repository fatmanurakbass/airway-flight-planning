package com.example.airwayflightplanning.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private final String message;
    private final String errorCode;

    public ErrorResponse(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

}