package com.example.airwayflightplanning.repository;

import com.example.airwayflightplanning.model.Flight;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class FlightRepositoryTest {
    @Autowired
    private FlightRepository flightRepository;

    @Test
    public void shouldCountFlightsBySourceAndDestinationAndDepartureDateRange() {
        Flight flight1 = new Flight();
        flight1.setSourceAirportCode("JFK");
        flight1.setDestinationAirportCode("LAX");
        flight1.setDepartureTime(LocalDateTime.now());
        flight1.setDuration(120);

        Flight flight2 = new Flight();
        flight2.setSourceAirportCode("JFK");
        flight2.setDestinationAirportCode("LAX");
        flight2.setDepartureTime(LocalDateTime.now().plusHours(1));
        flight2.setDuration(120);

        Flight flight3 = new Flight();
        flight3.setSourceAirportCode("JFK");
        flight3.setDestinationAirportCode("LAX");
        flight3.setDepartureTime(LocalDateTime.now().plusHours(2));
        flight3.setDuration(120);

        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);

        LocalDate departureDate = LocalDateTime.now().toLocalDate();
        LocalDate arrivalDate = LocalDateTime.now().plusDays(1).toLocalDate();
        long flightCount = flightRepository.countFlightsBySourceAndDestinationAndDepartureDateRange("JFK", "LAX", departureDate, arrivalDate);

        assertEquals(3, flightCount);
    }

    @Test
    public void shouldCountConflictingFlights() {
        Flight flight1 = new Flight();
        flight1.setSourceAirportCode("JFK");
        flight1.setDestinationAirportCode("LAX");
        flight1.setDepartureTime(LocalDateTime.now());
        flight1.setDuration(120);

        Flight flight2 = new Flight();
        flight2.setSourceAirportCode("JFK");
        flight2.setDestinationAirportCode("LAX");
        flight2.setDepartureTime(LocalDateTime.now().plusHours(1));
        flight2.setDuration(120);

        flightRepository.save(flight1);
        flightRepository.save(flight2);

        LocalDateTime testDepartureTime = LocalDateTime.now().plusMinutes(30);
        LocalDateTime testArrivalTime = testDepartureTime.plusMinutes(120);
        int conflictingFlightsCount = flightRepository.countConflictingFlights(testDepartureTime, testArrivalTime);

        assertEquals(2, conflictingFlightsCount);
    }
}
