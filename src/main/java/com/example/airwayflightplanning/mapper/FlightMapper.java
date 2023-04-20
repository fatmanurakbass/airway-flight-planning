package com.example.airwayflightplanning.mapper;

import com.example.airwayflightplanning.dto.FlightDto;
import com.example.airwayflightplanning.model.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);
    FlightDto modelToDto(Flight flight);

    List<FlightDto> modelsToDtos(List<Flight> flights);

    @Mapping(target = "id", ignore = true)
    Flight dtoToModel(FlightDto flightDto);
}
