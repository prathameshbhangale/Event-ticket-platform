package com.project.event_ticket_platform.service.impl;

import com.project.event_ticket_platform.domain.dto.*;
import com.project.event_ticket_platform.domain.enums.EventStatusEnum;
import com.project.event_ticket_platform.domain.model.Event;
import com.project.event_ticket_platform.domain.model.TicketType;
import com.project.event_ticket_platform.domain.model.User;
import com.project.event_ticket_platform.exceptions.EventNotFoundException;
import com.project.event_ticket_platform.exceptions.EventUpdateException;
import com.project.event_ticket_platform.exceptions.UserNotFoundException;
import com.project.event_ticket_platform.mapper.EventMapper;
import com.project.event_ticket_platform.repository.EventRepository;
import com.project.event_ticket_platform.repository.TicketTypeRepository;
import com.project.event_ticket_platform.repository.UserRepository;
import com.project.event_ticket_platform.service.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final TicketTypeRepository ticketTypeRepository;

    @Override
    public List<CreateEventResponseDto> listEvents(UUID oragnizerId, Pageable pagble) {
        Page<Event> events =  eventRepository.findByOrganizerId(oragnizerId, pagble);
        List<CreateEventResponseDto> response = events.stream()
                .map((event)->{
                    return eventMapper.toDto(event);
                })
                .collect(Collectors.toList());

        return response;
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public CreateEventResponseDto updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequestDto updateEventRequestDto) {
        Event event = eventMapper.toEntity(updateEventRequestDto);
        if(event.getId()==null || !event.getId().equals(id)) {
            throw new EventUpdateException("Event ID is null or not equal");
        }
        Event existingEvent = eventRepository.findByIdAndOrganizerId(id,organizerId).orElseThrow(()->{
            return new EventNotFoundException("Evant not exist");
        });

        existingEvent.setName(event.getName());
        existingEvent.setStart(event.getStart());
        existingEvent.setEnd(event.getEnd());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setSalesStart(event.getSalesStart());
        existingEvent.setSalesEnd(event.getSalesEnd());
        existingEvent.setStatus(event.getStatus());

        Set<UUID> requestTicketTypeIds = existingEvent.getTicketTypes()
                .stream()
                .map(TicketType::getId)
                .collect(Collectors.toSet());

        List<TicketType> ticketTypeList = new ArrayList<>();

        for(TicketType ticketType: event.getTicketTypes()){
            if(null == ticketType.getId()) {
                // Create
                TicketType ticketTypeToCreate = new TicketType();
                ticketTypeToCreate.setName(ticketType.getName());
                ticketTypeToCreate.setPrice(ticketType.getPrice());
                ticketTypeToCreate.setDescription(ticketType.getDescription());
                ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                ticketTypeToCreate.setEvent(existingEvent);
                ticketTypeList.add(ticketTypeToCreate);

            } else if(requestTicketTypeIds.contains(ticketType.getId())) {
                // Update
                TicketType existingTicketType = ticketType;
                existingTicketType.setName(ticketType.getName());
                existingTicketType.setPrice(ticketType.getPrice());
                existingTicketType.setDescription(ticketType.getDescription());
                existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());
                ticketTypeList.add(existingTicketType);
            } else {
                // delete
                // pass no need to add in list
                ticketTypeRepository.deleteById(ticketType.getId());
            }
        }

        existingEvent.setTicketTypes(ticketTypeList);
        return eventMapper.toDto(eventRepository.save(existingEvent));

    }

    @Override
    public CreateEventResponseDto getEventDetails(UUID oraganizerId, UUID id) {

        Event event = eventRepository.findById(id).orElseThrow(()->new EventNotFoundException("invalid event id"));
        if(!oraganizerId.equals(event.getOrganizer().getId())){
            throw new EventUpdateException("user not allow to access this event");
        }
        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public void deleteEvent(UUID oraganizerId, UUID id) {
        Event event = eventRepository.findByIdAndOrganizerId(id,oraganizerId).orElseThrow(()->new EventNotFoundException("invalid event id"));
        eventRepository.delete(event);
    }

    @Override
    public Page<CreateEventResponseDto> listPublishedEvents(Pageable pageable) {
        Page<CreateEventResponseDto> page = eventRepository
                .findByStatus(EventStatusEnum.PUBLISHED, pageable)
                .map(event -> eventMapper.toDto(event));
        return page;
    }

    @Override
    public Page<CreateEventResponseDto> searchPublishedEvents(String q, Pageable pageable) {
        Page<CreateEventResponseDto> page = eventRepository
                .searchEvents(q,pageable)
                .map(event -> eventMapper.toDto(event));
        return page;
    }

    @Override
    public GetPublishedEventDetailsResponseDto getPublishedEvent(UUID eventId) {
        Event event = eventRepository.findByIdAndStatus(eventId,EventStatusEnum.PUBLISHED).orElseThrow( ()-> new EventNotFoundException("event not found; check event id") );
        return eventMapper.toPublishedEventDto(event);
    }
}
