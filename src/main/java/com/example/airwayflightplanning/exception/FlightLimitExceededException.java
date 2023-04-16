package com.example.airwayflightplanning.exception;

import java.time.LocalDateTime;

public class FlightLimitExceededException extends ApiValidationException {
    public FlightLimitExceededException() {
        super("Daily flight limit exceeded between the given destinations.", "FLIGHT_LIMIT_EXCEEDED", LocalDateTime.now());
    }
}
