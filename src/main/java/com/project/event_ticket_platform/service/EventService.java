package com.project.event_ticket_platform.service;


import com.project.event_ticket_platform.domain.dto.CreateEventRequestDto;
import com.project.event_ticket_platform.domain.dto.CreateEventResponseDto;
import com.project.event_ticket_platform.domain.dto.UpdateEventRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Service
public interface EventService {
    public List<CreateEventResponseDto> listEvents(UUID oragnizerId, Pageable pageable);
    public CreateEventResponseDto createEvent(UUID organizerId, CreateEventRequestDto event);
    public CreateEventResponseDto updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequestDto event);
    public CreateEventResponseDto getEventDetails(UUID oraganizerId, UUID id);
}
