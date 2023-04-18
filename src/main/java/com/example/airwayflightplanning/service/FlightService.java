package com.example.airwayflightplanning.service;

import com.example.airwayflightplanning.dto.FlightDto;
import com.example.airwayflightplanning.exception.FlightLimitExceededException;
import com.example.airwayflightplanning.exception.FlightNotLandedException;
import com.example.airwayflightplanning.model.Flight;
import com.example.airwayflightplanning.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightService {
    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;

    public synchronized void createFlight(FlightDto flightDto){
        Flight flight = modelMapper.map(flightDto, Flight.class);
        validateFlightRules(flight);
        modelMapper.map(flightRepository.save(flight), FlightDto.class);
    }

    private void validateFlightRules(Flight flight) {
        // Business Rule 1: There must be daily at most 3 flights for an airline between 2 destinations.
        LocalDate departureDate = flight.getDepartureTime().toLocalDate();
        List<Flight> dailyFlights = flightRepository.findByAirlineCodeAndSourceAirportCodeAndDestinationAirportCodeAndDepartureTimeBetween(
                flight.getAirlineCode(),
                flight.getSourceAirportCode(),
                flight.getDestinationAirportCode(),
                departureDate.atStartOfDay(),
                departureDate.plusDays(1).atStartOfDay()
        );

        if (dailyFlights.size() >= 3) {
            throw new FlightLimitExceededException();
        }

        // Business Rule 2: New entry cannot be made until airplane landed.
        boolean hasOverlappingFlight = flightRepository.findAll().stream()
                .anyMatch(existingFlight ->
                        (flight.getDepartureTime().isEqual(existingFlight.getDepartureTime()) ||
                                flight.getDepartureTime().isAfter(existingFlight.getDepartureTime())) &&
                                flight.getDepartureTime().isBefore(existingFlight.getArrivalTime()) ||
                                flight.getArrivalTime().isAfter(existingFlight.getDepartureTime()) &&
                                flight.getDepartureTime().isBefore(existingFlight.getDepartureTime())
                );

        if (hasOverlappingFlight) {
                throw new FlightNotLandedException();
            }
    }

    public List<FlightDto> getFlights(){
        List<Flight> flights = flightRepository.findAll();
        return flights.stream().map(flight -> modelMapper.map(flight, FlightDto.class)).collect(Collectors.toList());
    }

}
