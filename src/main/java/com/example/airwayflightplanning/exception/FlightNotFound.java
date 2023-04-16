package com.example.airwayflightplanning.exception;

import java.time.LocalDateTime;

public class FlightNotFound extends ApiRequestException{
    public FlightNotFound(Long id) {
        super("Flight could not be found with id: " + id, "FLIGHT_NOT_FOUND", LocalDateTime.now());
    }
}
