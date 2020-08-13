package co.uk.kleindelao.demo.tennis.adapter.rest.controller;

import static org.mapstruct.ReportingPolicy.ERROR;

import co.uk.kleindelao.demo.tennis.domain.model.example.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface TimestampMapper {
    @Mapping(target = "formattedTimestamp", source = "dateTime")
    TimestampDto toDto(Timestamp timestamp);
}
