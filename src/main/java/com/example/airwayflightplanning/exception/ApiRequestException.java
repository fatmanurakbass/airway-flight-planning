package com.example.airwayflightplanning.exception;

public class ApiRequestException extends ApiException {
    public ApiRequestException(String message, String errorCode) {
        super(message, errorCode);
    }
}
