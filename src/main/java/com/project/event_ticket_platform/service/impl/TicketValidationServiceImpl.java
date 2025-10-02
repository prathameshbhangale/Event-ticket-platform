package com.project.event_ticket_platform.service.impl;

import com.project.event_ticket_platform.domain.enums.QrCodeStatusEnum;
import com.project.event_ticket_platform.domain.enums.TicketValidationMethod;
import com.project.event_ticket_platform.domain.enums.TicketValidationStatusEnum;
import com.project.event_ticket_platform.domain.model.QrCode;
import com.project.event_ticket_platform.domain.model.Ticket;
import com.project.event_ticket_platform.domain.model.TicketValidation;
import com.project.event_ticket_platform.exceptions.QrCodeNotFoundException;
import com.project.event_ticket_platform.exceptions.TicketNotFoundException;
import com.project.event_ticket_platform.repository.QrCodeRepository;
import com.project.event_ticket_platform.repository.TicketRepository;
import com.project.event_ticket_platform.repository.TicketValidationRepository;
import com.project.event_ticket_platform.service.TicketValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketValidationServiceImpl implements TicketValidationService {

    private final QrCodeRepository qrCodeRepository;
    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;

    @Override
    @Transactional
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(()->new TicketNotFoundException("no ticket found with id "+ ticketId));

        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(TicketValidationMethod.MANUAL);

        TicketValidationStatusEnum ticketValidationStatus = ticket.getValidations().stream()
                .filter(v -> TicketValidationStatusEnum.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);
        return ticketValidationRepository.save(ticketValidation);
    }

    @Transactional
    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException("QR Code not found or inactive with id "+ qrCodeId));

        Ticket ticket = qrCode.getTicket();
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(TicketValidationMethod.QR_SCAN);

        TicketValidationStatusEnum ticketValidationStatus = ticket.getValidations().stream()
                .filter(v -> TicketValidationStatusEnum.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);
        return ticketValidationRepository.save(ticketValidation);
    }
}
