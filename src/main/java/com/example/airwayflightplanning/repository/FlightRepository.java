package com.example.airwayflightplanning.repository;

import com.example.airwayflightplanning.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
//    List<Flight> findByAirlineCodeAndSourceAirportCodeAndDestinationAirportCodeAndDepartureTimeAndDuration(
//            String airlineCode, String sourceAirportCode, String destinationAirportCode, LocalDateTime departureTime, int duration
//    );

}