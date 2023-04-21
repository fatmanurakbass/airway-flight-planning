package com.example.airwayflightplanning.repository;

import com.example.airwayflightplanning.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT COUNT(f) FROM Flight f WHERE f.sourceAirportCode = ?1 AND f.destinationAirportCode = ?2 AND " +
            "TRUNC(f.departureTime) BETWEEN ?3 AND ?4")
    int countFlightsBySourceAndDestinationAndDepartureDateRange(String sourceAirportCode, String destinationAirportCode,
                                                                 LocalDate startDate, LocalDate endDate);


    @Query(value = "SELECT COUNT(*) FROM flight f WHERE " +
            "(:departureTime BETWEEN f.departure_time AND TIMESTAMPADD(MINUTE, f.duration, f.departure_time))" +
            "OR (:arrivalTime BETWEEN f.departure_time AND TIMESTAMPADD(MINUTE, f.duration, f.departure_time))",
            nativeQuery = true)
    int countConflictingFlights(@Param("departureTime") LocalDateTime departureTime,
                                @Param("arrivalTime") LocalDateTime arrivalTime);

}