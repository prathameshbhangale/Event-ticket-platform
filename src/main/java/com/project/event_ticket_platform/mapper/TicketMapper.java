package com.project.event_ticket_platform.mapper;


import com.project.event_ticket_platform.domain.dto.GetTicketResponseDto;
import com.project.event_ticket_platform.domain.dto.ListTicketResponseDto;
import com.project.event_ticket_platform.domain.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = { TicketTypeMapper.class }, imports = {  })
public interface TicketMapper {
    ListTicketResponseDto toListTicketResponseDto(Ticket ticket);

    @Mapping(target = "price", source = "ticket.ticketType.price")
    @Mapping(target = "description", source = "ticket.ticketType.description")
    @Mapping(target = "eventName", source = "ticket.ticketType.event.name")
    @Mapping(target = "eventVenue", source = "ticket.ticketType.event.venue")
    @Mapping(target = "eventStart", source = "ticket.ticketType.event.start")
    @Mapping(target = "eventEnd", source = "ticket.ticketType.event.end")
    GetTicketResponseDto toGetTicketResponseDto(Ticket ticket);
}
