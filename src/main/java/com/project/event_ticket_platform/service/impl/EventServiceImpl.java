package com.project.event_ticket_platform.service.impl;

import com.project.event_ticket_platform.domain.dto.CreateEventRequestDto;
import com.project.event_ticket_platform.domain.dto.CreateEventResponseDto;
import com.project.event_ticket_platform.domain.model.Event;
import com.project.event_ticket_platform.domain.model.TicketType;
import com.project.event_ticket_platform.domain.model.User;
import com.project.event_ticket_platform.exceptions.UserNotFoundException;
import com.project.event_ticket_platform.mapper.EventMapper;
import com.project.event_ticket_platform.repository.EventRepository;
import com.project.event_ticket_platform.repository.UserRepository;
import com.project.event_ticket_platform.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public CreateEventResponseDto createEvent(UUID organizerId, CreateEventRequestDto createEventDto) {
        Event event = eventMapper.toEntity(createEventDto);
        Event eventToCreate = new Event();

        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(
                ticketType -> {
                    TicketType ticketTypeToCreate = new TicketType();
                    ticketTypeToCreate.setName(ticketType.getName());
                    ticketTypeToCreate.setPrice(ticketType.getPrice());
                    ticketTypeToCreate.setDescription(ticketType.getDescription());
                    ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                    ticketTypeToCreate.setEvent(eventToCreate);
                    return ticketTypeToCreate;
                }).toList();
        User organizer = userRepository.findById(organizerId).orElseThrow(()->new UserNotFoundException("invalid organizer id"));

        eventToCreate.setName(event.getName());
        eventToCreate.setStart(event.getStart());
        eventToCreate.setEnd(event.getEnd());
        eventToCreate.setVenue(event.getVenue());
        eventToCreate.setSalesStart(event.getSalesStart());
        eventToCreate.setSalesEnd(event.getSalesEnd());
        eventToCreate.setStatus(event.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventMapper.toDto(eventRepository.save(eventToCreate));
    }
}
