package com.example.airwayflightplanning.controller;

import com.example.airwayflightplanning.dto.FlightDto;
import com.example.airwayflightplanning.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
@Slf4j
public class FlightController {
    private final FlightService flightService;
    @PostMapping
    public void createFlight(@RequestBody FlightDto flightDto){
        log.info("createFlight endpoint is called.");
        flightService.createFlight(flightDto);
    }

    @GetMapping
    public ResponseEntity<List<FlightDto>> getFlights(){
        return ResponseEntity.ok(flightService.getFlights());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable("id")Long id){
        log.info("getFlightById endpoint is called.");
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDto> updateFlight(@PathVariable("id")Long id, @RequestBody FlightDto flightDto){
        log.info("updateFlight endpoint is called.");
        return ResponseEntity.ok(flightService.updateFlight(id, flightDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteFlight(@PathVariable("id")Long id){
        log.info("deleteFlight endpoint is called.");
        Boolean status = flightService.deleteFlight(id);
        return ResponseEntity.ok(status);
    }
}
