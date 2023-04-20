package com.example.airwayflightplanning.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightDto {
    private String airlineCode;
    private String sourceAirportCode;
    private String destinationAirportCode;
    private LocalDateTime departureTime;
    private int duration;
}
