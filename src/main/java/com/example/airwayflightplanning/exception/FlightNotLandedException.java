package com.example.airwayflightplanning.exception;

public class FlightNotLandedException extends ApiValidationException {
    public FlightNotLandedException() {
        super("New entry cannot be made until airplane landed.", "FLIGHT_NOT_LANDED");
    }
}
