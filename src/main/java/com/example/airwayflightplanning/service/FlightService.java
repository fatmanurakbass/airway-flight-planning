package com.example.airwayflightplanning.service;

import com.example.airwayflightplanning.dto.FlightDto;
import com.example.airwayflightplanning.model.Flight;
import com.example.airwayflightplanning.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;

    public FlightDto createFlight(FlightDto flightDto){
        Flight flight = modelMapper.map(flightDto, Flight.class);
        return modelMapper.map(flightRepository.save(flight), FlightDto.class);
    }

    public List<FlightDto> getFlights(){
        List<Flight> flights = flightRepository.findAll();
        return flights.stream().map(flight -> modelMapper.map(flight, FlightDto.class)).collect(Collectors.toList());
    }

    public FlightDto getFlightById(Long id){
        Optional<Flight> flight = flightRepository.findById(id);
        if(flight.isPresent()){
            return modelMapper.map(flightRepository.findById(id), FlightDto.class);
        }
        return null;
    }

    public FlightDto updateFlight(Long id, FlightDto flightDto){
        Optional <Flight> resultFlight = flightRepository.findById(id);
        if (resultFlight.isPresent()){
            resultFlight.get().setAirlineCode(flightDto.getAirlineCode());
            resultFlight.get().setSourceAirportCode(flightDto.getSourceAirportCode());
            resultFlight.get().setDestinationAirportCode(flightDto.getDestinationAirportCode());
            resultFlight.get().setDepartureTime(flightDto.getDepartureTime());
            resultFlight.get().setArrivalTime(flightDto.getArrivalTime());
            resultFlight.get().setDuration(flightDto.getDuration());
            return modelMapper.map(flightRepository.save(resultFlight.get()), FlightDto.class);
        }
        return null;
    }

    public Boolean deleteFlight(Long id){
        Optional <Flight> resultFlight = flightRepository.findById(id);
        if (resultFlight.isPresent()){
            flightRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
