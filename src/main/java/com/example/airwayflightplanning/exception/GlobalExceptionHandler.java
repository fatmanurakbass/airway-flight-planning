package com.example.airwayflightplanning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode(), ex.getTimeStamp());
        HttpStatus status = getStatusForErrorCode(ex.getErrorCode());

        // Log error here
        System.err.println("Error: " + ex.getMessage());

        return new ResponseEntity<>(errorResponse, status);
    }

    private HttpStatus getStatusForErrorCode(String errorCode) {
        switch (errorCode) {
            case "FLIGHT_LIMIT_EXCEEDED":
            case "FLIGHT_NOT_LANDED":
                return HttpStatus.BAD_REQUEST;
            case "FLIGHT_NOT_FOUND":
                return HttpStatus.NOT_FOUND;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}