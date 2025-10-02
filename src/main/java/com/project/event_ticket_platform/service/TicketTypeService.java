package com.project.event_ticket_platform.service;

import com.project.event_ticket_platform.domain.model.Ticket;

import java.util.UUID;

public interface TicketTypeService {

    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}
