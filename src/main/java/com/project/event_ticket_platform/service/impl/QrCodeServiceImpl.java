package com.project.event_ticket_platform.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import com.project.event_ticket_platform.domain.enums.QrCodeStatusEnum;
import com.project.event_ticket_platform.domain.model.QrCode;
import com.project.event_ticket_platform.domain.model.Ticket;
import com.project.event_ticket_platform.exceptions.QrCodeGenerationException;
import com.project.event_ticket_platform.exceptions.QrCodeNotFoundException;
import com.project.event_ticket_platform.repository.QrCodeRepository;
import com.project.event_ticket_platform.service.QrCodeService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Data
@Service
@Slf4j
@RequiredArgsConstructor
public class QrCodeServiceImpl implements QrCodeService {

    private final QRCodeWriter qrCodeWriter;
    private final QrCodeRepository qrCodeRepository;

    private String generateImage(UUID uniqueId) throws WriterException, IOException
    {
        BitMatrix bitMatrix = qrCodeWriter.encode(
                uniqueId.toString(),
                BarcodeFormat.QR_CODE,
                QR_WIDTH,
                QR_HEIGHT
        );
        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(qrCodeImage, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }


    @Override
    public QrCode generateQrCode(Ticket ticket) {
        try{
            UUID uniqueId = UUID.randomUUID();
            String qrCodeImage = generateImage(uniqueId);

            QrCode qrCode = new QrCode();
            qrCode.setId(uniqueId);
            qrCode.setStatus(QrCodeStatusEnum.ACTIVE);
            log.error("Generated QR Code Image (Base64): {}", qrCodeImage); // Log the base64 string
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
            qrCode.setValue(qrCodeImage);
            qrCode.setTicket(ticket);

            QrCode q= qrCodeRepository.saveAndFlush(qrCode);
            return q;
        }catch (IOException | WriterException ex){
            throw new QrCodeGenerationException("Unabble to create QR CODE ",ex);
        }

    }

    @Override
    public byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId) {
        QrCode qrCode = qrCodeRepository.findByTicketIdAndTicketPurchaserId(ticketId, userId)
                .orElseThrow(QrCodeNotFoundException::new);

        try {
            return Base64.getDecoder().decode(qrCode.getValue());
        } catch(Exception ex) {
            log.error("Invalid base64 QR Code for ticket ID: {}", ticketId, ex);
            throw new QrCodeNotFoundException();
        }
    }

    @Override
    public String getQrCodeBase64( UUID userId, UUID ticketId) {
        QrCode qrCode = qrCodeRepository.findByTicketIdAndTicketPurchaserId(ticketId, userId)
                .orElseThrow(QrCodeNotFoundException::new);
        try {
            return qrCode.getValue();
        } catch(Exception ex) {
            log.error("Invalid base64 QR Code for ticket ID: {}", ticketId, ex);
            throw new QrCodeNotFoundException();
        }
    }

}
