package com.example.airwayflightplanning.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class FlightDto {
    private String airlineCode;
    private String sourceAirportCode;
    private String destinationAirportCode;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private LocalTime duration;
}
