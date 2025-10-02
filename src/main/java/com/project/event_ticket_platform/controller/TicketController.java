package com.project.event_ticket_platform.controller;

import com.project.event_ticket_platform.domain.dto.GetTicketResponseDto;
import com.project.event_ticket_platform.domain.dto.ListTicketResponseDto;
import com.project.event_ticket_platform.mapper.TicketMapper;
import com.project.event_ticket_platform.service.QrCodeService;
import com.project.event_ticket_platform.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final QrCodeService qrCodeService;

    @GetMapping
    public Page<ListTicketResponseDto> listTickets(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ) {
        return ticketService.listTicketsForUser(
                UUID.fromString(jwt.getClaimAsString("sub")),
                pageable
        ).map(ticketMapper::toListTicketResponseDto);
    }

    @GetMapping(path = "/{ticketId}")
    public ResponseEntity<GetTicketResponseDto> getTicket(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
    ) {
        return ticketService
                .getTicketForUser(UUID.fromString(jwt.getClaimAsString("sub")), ticketId)
                .map(ticketMapper::toGetTicketResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getTicketQrCode(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
    ) {
        byte[] qrCodeImage = qrCodeService.getQrCodeImageForUserAndTicket(
                UUID.fromString(jwt.getClaimAsString("sub")),
                ticketId
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(qrCodeImage.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(qrCodeImage);
    }

    // strings in base64 are typically about 33% larger than the original binary data
    // so we return it as a string directly instead of in a byte array
    // to avoid unnecessary complications with encoding
    // and to make it easier for clients to use directly in img tags or CSS
    // e.g., <img src="data:image/png;base64,..." />
    // easily integratable with react, angular, vue, etc.
    // also avoids issues with different clients handling binary data differently
    // and makes it more portable across different platforms and languages
    // however, for very large images, this might not be the most efficient way
    // but for QR codes, which are typically small, this is acceptable
    // also, this approach avoids issues with CORS and other security policies
    @GetMapping(path = "/{ticketId}/qr-codes/base64")
    public ResponseEntity<String> getTicketQrCodeAsBase64(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
    ){
        String qrCodeBase64 = qrCodeService.getQrCodeBase64(
                UUID.fromString(jwt.getClaimAsString("sub")),
                ticketId
        );
        return ResponseEntity.ok(qrCodeBase64);
    }
}
