package com.project.event_ticket_platform.mapper;

import com.project.event_ticket_platform.domain.dto.CreateTicketTypeRequestDto;
import com.project.event_ticket_platform.domain.dto.CreateTicketTypeResponseDto;
import com.project.event_ticket_platform.domain.model.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = { LocalDateTime.class })
public interface TicketTypeMapper {

    @Mapping(target = "event", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    TicketType toEntity(CreateTicketTypeRequestDto dto);

    CreateTicketTypeResponseDto toDto(TicketType ticketType);

    List<CreateTicketTypeResponseDto> toDtoList(List<TicketType> ticketTypes);
}
