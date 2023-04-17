package com.example.airwayflightplanning.controller;

import com.example.airwayflightplanning.dto.FlightDto;
import com.example.airwayflightplanning.service.FlightService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FlightController.class)
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldCreateFlightSuccessfully() throws Exception {
        FlightDto flightDto = new FlightDto();
        flightDto.setAirlineCode("AA");
        flightDto.setSourceAirportCode("JFK");
        flightDto.setDestinationAirportCode("LAX");
        flightDto.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightDto.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(6));
        flightDto.setDuration(LocalTime.of(6, 0));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String flightDtoJson = objectMapper.writeValueAsString(flightDto);

        mockMvc.perform(post("/flight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(flightDtoJson))
                .andExpect(status().isOk());

        verify(flightService, times(1)).createFlight(flightDto);
    }

    @Test
    public void shouldGetFlightsSuccessfully() throws Exception {
        FlightDto flightDto1 = new FlightDto();
        flightDto1.setAirlineCode("FN");
        flightDto1.setSourceAirportCode("IST");
        flightDto1.setDestinationAirportCode("JFK");
        flightDto1.setDepartureTime(LocalDateTime.parse("2023-04-20T18:30:00"));
        flightDto1.setArrivalTime(LocalDateTime.parse("2023-05-20T00:00:00"));
        flightDto1.setDuration(LocalTime.parse("05:30:00"));

        FlightDto flightDto2 = new FlightDto();
        flightDto2.setAirlineCode("FN");
        flightDto2.setSourceAirportCode("IST");
        flightDto2.setDestinationAirportCode("JFK");
        flightDto2.setDepartureTime(LocalDateTime.parse("2023-04-20T10:30:00"));
        flightDto2.setArrivalTime(LocalDateTime.parse("2023-04-20T16:00:00"));
        flightDto2.setDuration(LocalTime.parse("05:30:00"));

        List<FlightDto> flightDtos = Arrays.asList(flightDto1, flightDto2);
        when(flightService.getFlights()).thenReturn(flightDtos);

        mockMvc.perform(get("/flight")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"airlineCode\": \"FN\",\n" +
                        "        \"sourceAirportCode\": \"IST\",\n" +
                        "        \"destinationAirportCode\": \"JFK\",\n" +
                        "        \"departureTime\": \"2023-04-20T18:30:00\",\n" +
                        "        \"arrivalTime\": \"2023-05-20T00:00:00\",\n" +
                        "        \"duration\": \"05:30:00\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"airlineCode\": \"FN\",\n" +
                        "        \"sourceAirportCode\": \"IST\",\n" +
                        "        \"destinationAirportCode\": \"JFK\",\n" +
                        "        \"departureTime\": \"2023-04-20T10:30:00\",\n" +
                        "        \"arrivalTime\": \"2023-04-20T16:00:00\",\n" +
                        "        \"duration\": \"05:30:00\"\n" +
                        "    }\n" +
                        "]"));

        verify(flightService, times(1)).getFlights();
    }

    @Test
    public void shouldGetFlightByIdSuccessfully() throws Exception {
        Long flightId = 1L;
        FlightDto flightDto = new FlightDto();
        flightDto.setAirlineCode("FN");
        flightDto.setSourceAirportCode("IST");
        flightDto.setDestinationAirportCode("JFK");
        flightDto.setDepartureTime(LocalDateTime.parse("2023-04-20T18:30:00"));
        flightDto.setArrivalTime(LocalDateTime.parse("2023-05-20T00:00:00"));
        flightDto.setDuration(LocalTime.parse("05:30:00"));

        when(flightService.getFlightById(flightId)).thenReturn(flightDto);

        mockMvc.perform(get("/flight/{id}", flightId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"airlineCode\": \"FN\",\n" +
                        "    \"sourceAirportCode\": \"IST\",\n" +
                        "    \"destinationAirportCode\": \"JFK\",\n" +
                        "    \"departureTime\": \"2023-04-20T18:30:00\",\n" +
                        "    \"arrivalTime\": \"2023-05-20T00:00:00\",\n" +
                        "    \"duration\": \"05:30:00\"\n" +
                        "}"));

        verify(flightService, times(1)).getFlightById(flightId);
    }



    @Test
    public void shouldUpdateFlightSuccessfully() throws Exception {
        Long flightId = 1L;
        FlightDto flightDtoToUpdate = new FlightDto();
        flightDtoToUpdate.setAirlineCode("FN");
        flightDtoToUpdate.setSourceAirportCode("IST");
        flightDtoToUpdate.setDestinationAirportCode("JFK");
        flightDtoToUpdate.setDepartureTime(LocalDateTime.parse("2023-04-20T18:30:00"));
        flightDtoToUpdate.setArrivalTime(LocalDateTime.parse("2023-05-20T00:00:00"));
        flightDtoToUpdate.setDuration(LocalTime.parse("05:30:00"));

        FlightDto updatedFlightDto = new FlightDto();
        updatedFlightDto.setAirlineCode("FN");
        updatedFlightDto.setSourceAirportCode("IST");
        updatedFlightDto.setDestinationAirportCode("JFK");
        updatedFlightDto.setDepartureTime(LocalDateTime.parse("2023-04-20T18:30:00"));
        updatedFlightDto.setArrivalTime(LocalDateTime.parse("2023-05-20T00:00:00"));
        updatedFlightDto.setDuration(LocalTime.parse("05:30:00"));

        when(flightService.updateFlight(flightId, flightDtoToUpdate)).thenReturn(updatedFlightDto);

        mockMvc.perform(put("/flight/{id}", flightId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"airlineCode\": \"FN\",\"sourceAirportCode\": \"IST\",\"destinationAirportCode\": \"JFK\",\"departureTime\": \"2023-04-20T18:30:00\",\"arrivalTime\": \"2023-05-20T00:00:00\",\"duration\": \"05:30:00\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"airlineCode\": \"FN\",\n" +
                        "    \"sourceAirportCode\": \"IST\",\n" +
                        "    \"destinationAirportCode\": \"JFK\",\n" +
                        "    \"departureTime\": \"2023-04-20T18:30:00\",\n" +
                        "    \"arrivalTime\": \"2023-05-20T00:00:00\",\n" +
                        "    \"duration\": \"05:30:00\"\n" +
                        "}"));

        verify(flightService, times(1)).updateFlight(flightId, flightDtoToUpdate);
    }

    @Test
    public void shouldDeleteFlightSuccessfully() throws Exception {
        Long flightId = 1L;
        when(flightService.deleteFlight(flightId)).thenReturn(true);

        mockMvc.perform(delete("/flight/{id}", flightId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(flightService, times(1)).deleteFlight(flightId);
    }


    @After
    public void tearDown() throws Exception {
    }
}