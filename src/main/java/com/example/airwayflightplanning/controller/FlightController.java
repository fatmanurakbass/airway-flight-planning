package com.example.airwayflightplanning.controller;

import com.example.airwayflightplanning.dto.FlightDto;
import com.example.airwayflightplanning.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;
    @PostMapping
    public ResponseEntity<FlightDto> createFlight(@RequestBody FlightDto flightDto){
        return ResponseEntity.ok(flightService.createFlight(flightDto));
    }

    @GetMapping
    public ResponseEntity<List<FlightDto>> getFlights(){
        return ResponseEntity.ok(flightService.getFlights());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable("id")Long id){
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDto> updateFlight(@PathVariable("id")Long id, @RequestBody FlightDto flightDto){
        return ResponseEntity.ok(flightService.updateFlight(id, flightDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteFlight(@PathVariable("id")Long id){
        Boolean status = flightService.deleteFlight(id);
        return ResponseEntity.ok(status);
    }
}
