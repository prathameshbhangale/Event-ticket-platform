package com.project.event_ticket_platform.mapper;

import com.project.event_ticket_platform.domain.dto.CreateEventRequestDto;
import com.project.event_ticket_platform.domain.dto.CreateEventResponseDto;
import com.project.event_ticket_platform.domain.dto.UpdateEventRequestDto;
import com.project.event_ticket_platform.domain.model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { TicketTypeMapper.class }, imports = {  })
public interface EventMapper {

    Event toEntity(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

    Event toEntity(UpdateEventRequestDto dto);
}
