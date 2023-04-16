package com.example.airwayflightplanning.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode(), ex.getTimeStamp());
        HttpStatus status = getStatusForErrorCode(ex.getErrorCode());

        log.error("Error: " + ex.getMessage());

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