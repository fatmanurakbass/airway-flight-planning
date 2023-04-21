package com.example.airwayflightplanning.service;

import com.example.airwayflightplanning.dto.FlightDto;
import com.example.airwayflightplanning.exception.FlightLimitExceededException;
import com.example.airwayflightplanning.mapper.FlightMapper;
import com.example.airwayflightplanning.model.Flight;
import com.example.airwayflightplanning.repository.FlightRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {
    @InjectMocks
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightMapper flightMapper;

    @Test
    public void shouldThrowExceptionWhenDailyFlightLimitExceeded() {
        LocalDateTime departureTime = LocalDateTime.now();
        int flightDuration = 120;
        String sourceAirportCode = "JFK";
        String destinationAirportCode = "LAX";

        Flight flight = new Flight();
        flight.setDepartureTime(departureTime);
        flight.setDuration(flightDuration);
        flight.setSourceAirportCode(sourceAirportCode);
        flight.setDestinationAirportCode(destinationAirportCode);

        LocalDate departureDate = departureTime.toLocalDate();
        LocalDate arrivalDate = departureTime.plusMinutes(flightDuration).toLocalDate();

        when(flightRepository.countFlightsBySourceAndDestinationAndDepartureDateRange(
                sourceAirportCode, destinationAirportCode, departureDate, arrivalDate))
                .thenReturn(3);

        assertThrows(FlightLimitExceededException.class, () -> flightService.checkDailyFlightLimit(flight));
    }


    @Test
    public void shouldNotThrowExceptionWhenDailyFlightLimitNotExceeded() {

        LocalDateTime departureTime = LocalDateTime.now();
        int flightDuration = 120;
        String sourceAirportCode = "JFK";
        String destinationAirportCode = "LAX";

        Flight flight = new Flight();
        flight.setDepartureTime(departureTime);
        flight.setDuration(flightDuration);
        flight.setSourceAirportCode(sourceAirportCode);
        flight.setDestinationAirportCode(destinationAirportCode);

        LocalDate departureDate = departureTime.toLocalDate();
        LocalDate arrivalDate = departureTime.plusMinutes(flightDuration).toLocalDate();

        when(flightRepository.countFlightsBySourceAndDestinationAndDepartureDateRange(
                sourceAirportCode, destinationAirportCode, departureDate, arrivalDate))
                .thenReturn(2);

        flightService.checkDailyFlightLimit(flight);
    }

    @Test
    public void shouldRetrieveAllFlights() {
        Flight flight1 = new Flight();
        flight1.setSourceAirportCode("JFK");
        flight1.setDestinationAirportCode("LAX");
        flight1.setDepartureTime(LocalDateTime.now());
        flight1.setDuration(120);

        Flight flight2 = new Flight();
        flight2.setSourceAirportCode("SFO");
        flight2.setDestinationAirportCode("ORD");
        flight2.setDepartureTime(LocalDateTime.now().plusHours(2));
        flight2.setDuration(180);

        List<Flight> flights = Arrays.asList(flight1, flight2);

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

        List<FlightDto> expectedFlightDtos = Arrays.asList(flightDto1, flightDto2);

        when(flightRepository.findAll()).thenReturn(flights);
        when(flightMapper.modelsToDtos(flights)).thenReturn(expectedFlightDtos);

        List<FlightDto> flightDtos = flightService.getFlights();

        assertEquals(expectedFlightDtos, flightDtos);
        verify(flightRepository).findAll();
        verify(flightMapper).modelsToDtos(flights);
    }
}
