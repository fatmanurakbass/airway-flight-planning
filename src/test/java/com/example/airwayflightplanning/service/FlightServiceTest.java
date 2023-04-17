package com.example.airwayflightplanning.service;

import com.example.airwayflightplanning.dto.FlightDto;
import com.example.airwayflightplanning.exception.FlightLimitExceededException;
import com.example.airwayflightplanning.exception.FlightNotFound;
import com.example.airwayflightplanning.exception.FlightNotLandedException;
import com.example.airwayflightplanning.model.Flight;
import com.example.airwayflightplanning.repository.FlightRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FlightServiceTest {

    private FlightService flightService;
    private FlightRepository flightRepository;
    private ModelMapper modelMapper;
    @Before
    public void setUp() throws Exception {
        flightRepository = Mockito.mock(FlightRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);

        flightService = new FlightService(flightRepository, modelMapper);
    }

    @Test
    public void shouldCreateNewFlight() {
        FlightDto flightDto = new FlightDto();
        flightDto.setAirlineCode("FN");
        flightDto.setSourceAirportCode("JFK");
        flightDto.setDestinationAirportCode("IST");
        flightDto.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightDto.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(6));

        Flight flight = new Flight();
        flight.setAirlineCode(flightDto.getAirlineCode());
        flight.setSourceAirportCode(flightDto.getSourceAirportCode());
        flight.setDestinationAirportCode(flightDto.getDestinationAirportCode());
        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setArrivalTime(flightDto.getArrivalTime());

        when(modelMapper.map(flightDto, Flight.class)).thenReturn(flight);
        when(modelMapper.map(flight, FlightDto.class)).thenReturn(flightDto);
        when(flightRepository.save(flight)).thenReturn(flight);

        flightService.createFlight(flightDto);

        verify(modelMapper, times(1)).map(flightDto, Flight.class);
        verify(modelMapper, times(1)).map(flight, FlightDto.class);
        verify(flightRepository, times(1)).save(flight);
    }

    @Test
    public void shouldNotCreateFlightWhenDailyFlightLimitExceeded() {
        FlightDto flightDto = new FlightDto();
        flightDto.setAirlineCode("FN");
        flightDto.setSourceAirportCode("JFK");
        flightDto.setDestinationAirportCode("IST");
        flightDto.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightDto.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(6));

        Flight flight = new Flight();
        flight.setAirlineCode(flightDto.getAirlineCode());
        flight.setSourceAirportCode(flightDto.getSourceAirportCode());
        flight.setDestinationAirportCode(flightDto.getDestinationAirportCode());
        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setArrivalTime(flightDto.getArrivalTime());

        when(modelMapper.map(flightDto, Flight.class)).thenReturn(flight);
        when(modelMapper.map(flight, FlightDto.class)).thenReturn(flightDto);

        // Create a list of 3 flights to simulate the daily limit
        List<Flight> dailyFlights = Arrays.asList(new Flight(), new Flight(), new Flight());
        LocalDate departureDate = flight.getDepartureTime().toLocalDate();
        when(flightRepository.findByAirlineCodeAndSourceAirportCodeAndDestinationAirportCodeAndDepartureTimeBetween(
                anyString(),
                anyString(),
                anyString(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(dailyFlights);

        try {
            flightService.createFlight(flightDto);
            fail("Expected a FlightLimitExceededException to be thrown");
        } catch (FlightLimitExceededException e) {
            // Test passes if this exception is thrown
        }

        verify(modelMapper, times(1)).map(flightDto, Flight.class);
        verify(flightRepository, never()).save(flight);
    }

    @Test
    public void shouldNotCreateFlightWhenAirplaneHasNotLanded() {
        FlightDto flightDto = new FlightDto();
        flightDto.setAirlineCode("FN");
        flightDto.setSourceAirportCode("JFK");
        flightDto.setDestinationAirportCode("IST");
        flightDto.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightDto.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(6));

        Flight flight = new Flight();
        flight.setAirlineCode(flightDto.getAirlineCode());
        flight.setSourceAirportCode(flightDto.getSourceAirportCode());
        flight.setDestinationAirportCode(flightDto.getDestinationAirportCode());
        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setArrivalTime(flightDto.getArrivalTime());

        when(modelMapper.map(flightDto, Flight.class)).thenReturn(flight);
        when(modelMapper.map(flight, FlightDto.class)).thenReturn(flightDto);

        // Create a list of flights with overlapping departure and arrival times
        Flight overlappingFlight = new Flight();
        overlappingFlight.setDepartureTime(flight.getDepartureTime().minusHours(1));
        overlappingFlight.setArrivalTime(flight.getArrivalTime().minusHours(1));

        List<Flight> flights = Collections.singletonList(overlappingFlight);
        when(flightRepository.findAll()).thenReturn(flights);

        try {
            flightService.createFlight(flightDto);
            fail("Expected a FlightNotLandedException to be thrown");
        } catch (FlightNotLandedException e) {
            // Test passes if this exception is thrown
        }

        verify(modelMapper, times(1)).map(flightDto, Flight.class);
        verify(flightRepository, never()).save(flight);
    }

    @Test
    public void shouldReturnAllFlightsAsFlightDtos() {
        Flight flight1 = new Flight();
        flight1.setId(1L);
        flight1.setAirlineCode("FN");
        flight1.setSourceAirportCode("JFK");
        flight1.setDestinationAirportCode("IST");
        flight1.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight1.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(6));

        Flight flight2 = new Flight();
        flight2.setId(2L);
        flight2.setAirlineCode("FB");
        flight2.setSourceAirportCode("JFK");
        flight2.setDestinationAirportCode("SFO");
        flight2.setDepartureTime(LocalDateTime.now().plusDays(2));
        flight2.setArrivalTime(LocalDateTime.now().plusDays(2).plusHours(6));

        List<Flight> flights = Arrays.asList(flight1, flight2);

        when(flightRepository.findAll()).thenReturn(flights);

        when(modelMapper.map(flight1, FlightDto.class)).thenReturn(new FlightDto());
        when(modelMapper.map(flight2, FlightDto.class)).thenReturn(new FlightDto());

        List<FlightDto> flightDtos = flightService.getFlights();

        assertNotNull(flightDtos);
        assertEquals(flights.size(), flightDtos.size());

        verify(flightRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(flight1, FlightDto.class);
        verify(modelMapper, times(1)).map(flight2, FlightDto.class);
    }

    @Test
    public void shouldReturnFlightDtoWhenFlightIdIsFound() {
        // Create a flight
        Long flightId = 1L;
        Flight flight = new Flight();
        flight.setId(flightId);
        flight.setAirlineCode("AA");
        flight.setSourceAirportCode("JFK");
        flight.setDestinationAirportCode("LAX");
        flight.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(6));

        // Set the behavior of the flightRepository mock object
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));

        // Set the behavior of the modelMapper mock object
        FlightDto flightDto = new FlightDto();
        when(modelMapper.map(flight, FlightDto.class)).thenReturn(flightDto);

        // Call the getFlightById method and check the result
        FlightDto result = flightService.getFlightById(flightId);

        assertNotNull(result);
        assertEquals(flightDto, result);

        verify(flightRepository, times(1)).findById(flightId);
        verify(modelMapper, times(1)).map(flight, FlightDto.class);
    }

    @Test
    public void shouldThrowFlightNotFoundWhenFlightIdIsNotFound() {
        Long flightId = 1L;

        // Set the behavior of the flightRepository mock object
        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        try {
            flightService.getFlightById(flightId);
            fail("Expected a FlightNotFound exception to be thrown");
        } catch (FlightNotFound e) {
            assertTrue(e.getMessage().contains("Flight could not be found with id: " + flightId));
        }

        verify(flightRepository, times(1)).findById(flightId);
        verify(modelMapper, never()).map(any(Flight.class), eq(FlightDto.class));
    }

    @Test
    public void shouldThrowFlightNotFoundWhenUpdatingNonExistingFlight() {
        Long flightId = 1L;
        FlightDto flightDto = new FlightDto();

        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        try {
            flightService.updateFlight(flightId, flightDto);
            fail("Expected a FlightNotFound exception to be thrown");
        } catch (FlightNotFound e) {
            assertTrue(e.getMessage().contains("Flight could not be found with id: " + flightId));
        }

        verify(flightRepository, times(1)).findById(flightId);
        verify(flightRepository, never()).save(any(Flight.class));
        verify(modelMapper, never()).map(any(Flight.class), eq(FlightDto.class));
    }

    @Test
    public void shouldUpdateFlightSuccessfullyWhenFlightIdIsFound() {
        Long flightId = 1L;
        Flight flight = new Flight();
        flight.setId(flightId);
        flight.setAirlineCode("AA");
        flight.setSourceAirportCode("JFK");
        flight.setDestinationAirportCode("LAX");
        flight.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(6));
        flight.setDuration(LocalTime.of(6, 0));

        FlightDto flightDto = new FlightDto();
        flightDto.setAirlineCode("BB");
        flightDto.setSourceAirportCode("SFO");
        flightDto.setDestinationAirportCode("ORD");
        flightDto.setDepartureTime(LocalDateTime.now().plusDays(2));
        flightDto.setArrivalTime(LocalDateTime.now().plusDays(2).plusHours(4));
        flightDto.setDuration(LocalTime.of(4, 0));

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        when(modelMapper.map(flight, FlightDto.class)).thenReturn(flightDto);

        FlightDto updatedFlightDto = flightService.updateFlight(flightId, flightDto);

        assertNotNull(updatedFlightDto);
        assertEquals(flightDto, updatedFlightDto);
        verify(flightRepository, times(1)).findById(flightId);
        verify(flightRepository, times(1)).save(flight);
        verify(modelMapper, times(1)).map(flight, FlightDto.class);
    }

    @Test
    public void shouldDeleteFlightSuccessfullyWhenFlightIdIsFound() {
        Long flightId = 1L;
        Flight flight = new Flight();
        flight.setId(flightId);

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));

        Boolean isDeleted = flightService.deleteFlight(flightId);

        assertTrue(isDeleted);
        verify(flightRepository, times(1)).findById(flightId);
        verify(flightRepository, times(1)).deleteById(flightId);
    }

    @Test
    public void shouldThrowFlightNotFoundWhenDeletingNonExistingFlight() {
        Long flightId = 1L;

        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        try {
            flightService.deleteFlight(flightId);
            fail("Expected a FlightNotFound exception to be thrown");
        } catch (FlightNotFound e) {
            assertTrue(e.getMessage().contains("Flight could not be found with id: " + flightId));
        }

        verify(flightRepository, times(1)).findById(flightId);
        verify(flightRepository, never()).deleteById(flightId);
    }


    @After
    public void tearDown() throws Exception {

    }
}