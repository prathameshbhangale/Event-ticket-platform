package com.project.event_ticket_platform.domain.dto;


import java.util.UUID;

import com.project.event_ticket_platform.domain.enums.TicketValidationMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationRequestDto {
    // QrcodeId of QrCode method and ticketId of Manual method
    private UUID id;
    private TicketValidationMethod method;
}