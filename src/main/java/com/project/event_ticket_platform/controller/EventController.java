package com.project.event_ticket_platform.controller;


import com.project.event_ticket_platform.domain.dto.CreateEventRequestDto;
import com.project.event_ticket_platform.domain.dto.CreateEventResponseDto;
import com.project.event_ticket_platform.domain.dto.UpdateEventRequestDto;
import com.project.event_ticket_platform.mapper.EventMapper;
import com.project.event_ticket_platform.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto
    ){
        UUID userId = UUID.fromString(jwt.getClaimAsString("sub"));
        CreateEventResponseDto response = eventService.createEvent(userId,createEventRequestDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/{eventId}")
    public ResponseEntity<CreateEventResponseDto> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto
    ){
        UUID userId = UUID.fromString(jwt.getClaimAsString("sub"));
        CreateEventResponseDto response = eventService.updateEventForOrganizer(userId,eventId,updateEventRequestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CreateEventResponseDto>> listEvent(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ){
        UUID userId = UUID.fromString(jwt.getClaimAsString("sub"));
        List<CreateEventResponseDto> response = eventService.listEvents(userId,pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<CreateEventResponseDto> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ){
        UUID userId = UUID.fromString(jwt.getClaimAsString("sub"));
        CreateEventResponseDto response = eventService.getEventDetails(userId,eventId);
        return ResponseEntity.ok(response);
    }
}
