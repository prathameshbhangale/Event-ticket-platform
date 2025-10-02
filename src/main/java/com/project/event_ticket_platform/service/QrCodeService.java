package com.project.event_ticket_platform.service;

import com.project.event_ticket_platform.domain.model.QrCode;
import com.project.event_ticket_platform.domain.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface QrCodeService {
    int QR_HEIGHT = 300;
    int QR_WIDTH = 300;

    QrCode generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);

    String getQrCodeBase64(UUID userId, UUID ticketId);
}
