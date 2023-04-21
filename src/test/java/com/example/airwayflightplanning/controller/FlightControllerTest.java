package com.example.airwayflightplanning.controller;

import com.example.airwayflightplanning.dto.FlightDto;
import com.example.airwayflightplanning.service.FlightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FlightControllerTest {
    @InjectMocks
    private FlightController flightController;

    @Mock
    private FlightService flightService;

    @Test
    public void createFlight() {
        FlightDto flightDto = new FlightDto();
        flightDto.setSourceAirportCode("JFK");
        flightDto.setDestinationAirportCode("LAX");
        flightDto.setDepartureTime(LocalDateTime.now());
        flightDto.setDuration(120);

        flightController.createFlight(flightDto);

        verify(flightService).createFlight(any(FlightDto.class));
    }
    @Test
    public void getFlights() {
        FlightDto flightDto1 = new FlightDto();
        flightDto1.setSourceAirportCode("JFK");
        flightDto1.setDestinationAirportCode("LAX");
        flightDto1.setDepartureTime(LocalDateTime.now());
        flightDto1.setDuration(120);

        FlightDto flightDto2 = new FlightDto();
        flightDto2.setSourceAirportCode("SFO");
        flightDto2.setDestinationAirportCode("ORD");
        flightDto2.setDepartureTime(LocalDateTime.now().plusHours(2));
        flightDto2.setDuration(180);

        List<FlightDto> expectedFlights = Arrays.asList(flightDto1, flightDto2);
        when(flightService.getFlights()).thenReturn(expectedFlights);

        ResponseEntity<List<FlightDto>> responseEntity = flightController.getFlights();
        assertEquals(expectedFlights, responseEntity.getBody());
    }
}