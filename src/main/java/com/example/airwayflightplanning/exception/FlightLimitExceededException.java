package com.example.airwayflightplanning.exception;

public class FlightLimitExceededException extends RuntimeException {
    public FlightLimitExceededException(String message) {
        super(message);
    }
}
