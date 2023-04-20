package com.example.airwayflightplanning.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class FlightDto {

    @NotNull(message = "airlineCode cannot be empty.")
    private String airlineCode;
    @NotNull(message = "sourceAirportCode cannot be null.")
    private String sourceAirportCode;
    @NotNull(message = "destinationAirportCode cannot be null.")
    private String destinationAirportCode;
    @NotNull(message = "departureTime cannot be null.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime departureTime;
    @NotNull(message = "Duration cannot be null.")
    @Min(0)
    private int duration;
}
