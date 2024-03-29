package com.example.airwayflightplanning.controller;

import com.example.airwayflightplanning.dto.FlightDto;
import com.example.airwayflightplanning.service.FlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/flight")
@Slf4j
public class FlightController {
    @Autowired
    private FlightService flightService;
    @PostMapping
    public void createFlight(@Valid @RequestBody FlightDto flightDto){
        log.info("createFlight endpoint is called.");
        flightService.createFlight(flightDto);
    }

    @GetMapping
    public ResponseEntity<List<FlightDto>> getFlights(){
        log.info("getFlights endpoint is called.");
        return ResponseEntity.ok(flightService.getFlights());
    }

}
