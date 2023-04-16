package com.example.airwayflightplanning.exception;

import java.time.LocalDateTime;

public class ApiRequestException extends ApiException {
    public ApiRequestException(String message, String errorCode, LocalDateTime timeStamp) {
        super(message, errorCode, timeStamp);
    }
}
