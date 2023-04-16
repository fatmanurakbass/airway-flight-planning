package com.example.airwayflightplanning.exception;

import java.time.LocalDateTime;

public class ApiValidationException extends ApiException {
    public ApiValidationException(String message, String errorCode, LocalDateTime timeStamp) {
        super(message, errorCode, timeStamp);
    }
}
