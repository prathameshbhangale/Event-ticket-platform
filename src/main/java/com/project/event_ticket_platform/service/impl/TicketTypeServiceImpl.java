package com.project.event_ticket_platform.service.impl;

import com.project.event_ticket_platform.domain.enums.TicketStatusEnum;
import com.project.event_ticket_platform.domain.model.Ticket;
import com.project.event_ticket_platform.domain.model.TicketType;
import com.project.event_ticket_platform.domain.model.User;
import com.project.event_ticket_platform.exceptions.TicketTypeNotFoundException;
import com.project.event_ticket_platform.exceptions.UserNotFoundException;
import com.project.event_ticket_platform.repository.TicketRepository;
import com.project.event_ticket_platform.repository.TicketTypeRepository;
import com.project.event_ticket_platform.repository.UserRepository;
import com.project.event_ticket_platform.service.QrCodeService;
import com.project.event_ticket_platform.service.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id "+userId));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId).orElseThrow(
                ()->{
                    throw new TicketTypeNotFoundException("Ticket type not found with id "+ticketTypeId);
                });

        int purchedTickets = ticketRepository.countByTicketTypeId(ticketTypeId);
        int totalAvailable = ticketType.getTotalAvailable();

        if(purchedTickets > totalAvailable -1 ){
            throw new TicketTypeNotFoundException("Ticket type with id "+ticketTypeId+" is sold out");

        }
        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);
        Ticket savedTicket = ticketRepository.save(ticket);

        qrCodeService.generateQrCode(savedTicket);
        return savedTicket;
    }
}
