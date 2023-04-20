package com.example.airwayflightplanning.service;

import com.example.airwayflightplanning.dto.FlightDto;
import com.example.airwayflightplanning.mapper.FlightMapper;
import com.example.airwayflightplanning.model.Flight;
import com.example.airwayflightplanning.repository.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightMapper flightMapper;


    public synchronized void createFlight(FlightDto flightDto){
        Flight flight = flightMapper.dtoToModel(flightDto);
        // validateFlightRules(flight);
        flightRepository.save(flight);
    }

//    private void validateFlightRules(Flight flight) {
//        // Business Rule 1: There must be daily at most 3 flights for an airline between 2 destinations.
//        LocalDate departureDate = flight.getDepartureTime().toLocalDate();
//        List<Flight> dailyFlights = flightRepository.findByAirlineCodeAndSourceAirportCodeAndDestinationAirportCodeAndDepartureTimeBetween(
//                flight.getAirlineCode(),
//                flight.getSourceAirportCode(),
//                flight.getDestinationAirportCode(),
//                departureDate.atStartOfDay(),
//                departureDate.plusDays(1).atStartOfDay()
//        );
//
//        if (dailyFlights.size() >= 3) {
//            throw new FlightLimitExceededException();
//        }
//
//        // Business Rule 2: New entry cannot be made until airplane landed.
//        boolean hasOverlappingFlight = flightRepository.findAll().stream()
//                .anyMatch(existingFlight ->
//                        (flight.getDepartureTime().isEqual(existingFlight.getDepartureTime()) ||
//                                flight.getDepartureTime().isAfter(existingFlight.getDepartureTime())) &&
//                                flight.getDepartureTime().isBefore(existingFlight.getArrivalTime()) ||
//                                flight.getArrivalTime().isAfter(existingFlight.getDepartureTime()) &&
//                                flight.getDepartureTime().isBefore(existingFlight.getDepartureTime())
//                );
//
//        if (hasOverlappingFlight) {
//                throw new FlightNotLandedException();
//            }
//    }

    public List<FlightDto> getFlights(){
        return (flightMapper.modelsToDtos(flightRepository.findAll()));
    }

}
