package com.example.airwayflightplanning.repository;

import com.example.airwayflightplanning.model.Flight;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class FlightRepositoryTest {
    @Autowired
    private FlightRepository flightRepository;

    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void shouldFindByAirlineCodeAndSourceAirportCodeAndDestinationAirportCodeAndDepartureTimeBetween() {
        Flight flight1 = new Flight();
        flight1.setAirlineCode("FN");
        flight1.setSourceAirportCode("IST");
        flight1.setDestinationAirportCode("JFK");
        flight1.setDepartureTime(LocalDateTime.of(2023, 4, 20, 18, 30));
        flight1.setArrivalTime(LocalDateTime.of(2023, 4, 21, 0, 0));

        Flight flight2 = new Flight();
        flight2.setAirlineCode("FN");
        flight2.setSourceAirportCode("IST");
        flight2.setDestinationAirportCode("JFK");
        flight2.setDepartureTime(LocalDateTime.of(2023, 4, 21, 10, 30));
        flight2.setArrivalTime(LocalDateTime.of(2023, 4, 21, 16, 0));

        flightRepository.save(flight1);
        flightRepository.save(flight2);

        LocalDateTime startDate = LocalDateTime.of(2023, 4, 20, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 4, 21, 23, 59);

        List<Flight> flights = flightRepository.findByAirlineCodeAndSourceAirportCodeAndDestinationAirportCodeAndDepartureTimeBetween(
                "FN", "IST", "JFK", startDate, endDate);

        assertThat(flights.size()).isEqualTo(2);
        assertThat(flights).contains(flight1, flight2);
    }

    @After
    public void tearDown() throws Exception {
    }
}