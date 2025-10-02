package com.project.event_ticket_platform.controller;


import com.project.event_ticket_platform.service.TicketTypeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/events/{eventId}/ticket-types")
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;


    @PostMapping(path = "/{ticketTypeId}/tickets")
    public ResponseEntity<?> purchaseTicket(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketTypeId
    ){
        UUID userId = UUID.fromString(jwt.getClaimAsString("sub"));
        ticketTypeService.purchaseTicket(userId, ticketTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
