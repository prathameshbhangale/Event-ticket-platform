package com.project.event_ticket_platform.mapper;

import com.project.event_ticket_platform.domain.dto.*;
import com.project.event_ticket_platform.domain.model.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = { LocalDateTime.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TicketTypeMapper {

    @Mapping(target = "event", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    TicketType toEntity(CreateTicketTypeRequestDto dto);

    CreateTicketTypeResponseDto toDto(TicketType ticketType);

    List<CreateTicketTypeResponseDto> toDtoList(List<TicketType> ticketTypes);

//    @Mapping(target = "event", ignore = true)
//    @Mapping(target = "tickets", ignore = true)
    TicketType toEntity(UpdateTicketTypeRequestDto dto);

//    @Mapping(target = "totalAvailable", ignore = true)
    GetPublishedEventDetailsTicketTypesResponseDto toPublishedEventDto(TicketType ticketType);

    ListTicketTicketTypeResponseDto toListTicketTicketTypeResponseDto(TicketType ticketType);
}
