package com.project.event_ticket_platform.service;


import com.project.event_ticket_platform.domain.dto.CreateEventRequestDto;
import com.project.event_ticket_platform.domain.dto.CreateEventResponseDto;
import com.project.event_ticket_platform.domain.dto.UpdateEventRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Service
public interface EventService {
    List<CreateEventResponseDto> listEvents(UUID oragnizerId, Pageable pageable);
    CreateEventResponseDto createEvent(UUID organizerId, CreateEventRequestDto event);
    CreateEventResponseDto updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequestDto event);
    CreateEventResponseDto getEventDetails(UUID oraganizerId, UUID id);
    void deleteEvent(UUID oraganizerId, UUID id);
    Page<CreateEventResponseDto> listPublishedEvents(Pageable pageable);
    Page<CreateEventResponseDto> searchPublishedEvents(String q,Pageable pageable);
}
