package com.example.airwayflightplanning.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private String errorCode;
    private LocalDateTime timeStamp;

    public ErrorResponse(String message, String errorCode, LocalDateTime timeStamp) {
        this.message = message;
        this.errorCode = errorCode;
        this.timeStamp = timeStamp;
    }

}