package com.example.airwayflightplanning.exception;

import java.time.LocalDateTime;

public abstract class ApiException extends RuntimeException {
    private final String errorCode;
    private final LocalDateTime timeStamp;

    public ApiException(String message, String errorCode, LocalDateTime timeStamp) {
        super(message);
        this.errorCode = errorCode;
        this.timeStamp = timeStamp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
