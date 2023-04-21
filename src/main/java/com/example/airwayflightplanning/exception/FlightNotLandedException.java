package com.example.airwayflightplanning.exception;


public class FlightNotLandedException extends RuntimeException {
    public FlightNotLandedException(String message) {
        super(message);
    }
}
