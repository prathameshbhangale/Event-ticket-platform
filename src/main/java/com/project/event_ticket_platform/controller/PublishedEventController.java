package com.project.event_ticket_platform.controller;

import com.project.event_ticket_platform.domain.dto.CreateEventResponseDto;
import com.project.event_ticket_platform.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<?> listPublishEvents(
            @RequestParam(required = false) String q,
            Pageable pageable
    ){
        Page<CreateEventResponseDto> page;
        if(q==null){
             page = eventService.listPublishedEvents(pageable);
        }else{
            page = eventService.searchPublishedEvents(q,pageable);
        }
        return ResponseEntity.ok(page);
    }
}
