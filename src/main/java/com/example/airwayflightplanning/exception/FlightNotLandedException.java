package com.example.airwayflightplanning.exception;

import java.time.LocalDateTime;

public class FlightNotLandedException extends ApiValidationException {
    public FlightNotLandedException() {
        super("New entry cannot be made until airplane landed.", "FLIGHT_NOT_LANDED", LocalDateTime.now());
    }
}
