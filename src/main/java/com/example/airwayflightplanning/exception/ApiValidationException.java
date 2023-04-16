package com.example.airwayflightplanning.exception;

public class ApiValidationException extends ApiException {
    public ApiValidationException(String message, String errorCode) {
        super(message, errorCode);
    }
}
