package com.project.event_ticket_platform.service;


import com.project.event_ticket_platform.domain.dto.CreateEventRequestDto;
import com.project.event_ticket_platform.domain.dto.CreateEventResponseDto;
import com.project.event_ticket_platform.domain.dto.UpdateEventRequestDto;
import com.project.event_ticket_platform.domain.model.Event;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface EventService {
    public CreateEventResponseDto createEvent(UUID organizerId, CreateEventRequestDto event);
    public CreateEventResponseDto updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequestDto event);
}
