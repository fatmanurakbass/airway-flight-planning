package com.example.airwayflightplanning.service;

import com.example.airwayflightplanning.dto.FlightDto;
import com.example.airwayflightplanning.exception.FlightLimitExceededException;
import com.example.airwayflightplanning.exception.FlightNotLandedException;
import com.example.airwayflightplanning.mapper.FlightMapper;
import com.example.airwayflightplanning.model.Flight;
import com.example.airwayflightplanning.repository.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        checkDailyFlightLimit(flight);
        checkEntryBeforeAirplaneLanding(flight);
        flightRepository.save(flight);
    }

    synchronized void checkDailyFlightLimit(Flight flight) {

        LocalDate departureDate = flight.getDepartureTime().toLocalDate();
        LocalDate arrivalDate = flight.getDepartureTime().plusMinutes(flight.getDuration()).toLocalDate();

        int flightsCount = flightRepository.countFlightsBySourceAndDestinationAndDepartureDateRange(
                flight.getSourceAirportCode(), flight.getDestinationAirportCode(), departureDate, arrivalDate);

        if (flightsCount >= 3) {
            throw new FlightLimitExceededException("Daily maximum flights exceeded for this airline and route.");
        }
    }
    synchronized void checkEntryBeforeAirplaneLanding(Flight flight) {

        int nonConflictingFlights = flightRepository.countConflictingFlights(flight.getDepartureTime(),
                                                    flight.getDepartureTime().plusMinutes(flight.getDuration()));

        if (nonConflictingFlights != 0) {
            throw new FlightNotLandedException("The airplane has not landed yet. Cannot add a new flight entry.");
        }
    }

    public List<FlightDto> getFlights(){
        return (flightMapper.modelsToDtos(flightRepository.findAll()));
    }

}
