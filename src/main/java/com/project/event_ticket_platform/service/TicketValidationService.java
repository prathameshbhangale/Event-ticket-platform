package com.project.event_ticket_platform.service;

import com.project.event_ticket_platform.domain.model.TicketValidation;
import java.util.UUID;


public interface TicketValidationService {
    TicketValidation validateTicketByQrCode(UUID qrCodeId);
    TicketValidation validateTicketManually(UUID ticketId);
}
