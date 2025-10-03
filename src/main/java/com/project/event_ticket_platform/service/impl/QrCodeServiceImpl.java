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

    private static final int QR_WIDTH = 200;
    private static final int QR_HEIGHT = 200;

    private final QRCodeWriter qrCodeWriter;
    private final QrCodeRepository qrCodeRepository;

    private byte[] generateImage(UUID uniqueId) throws WriterException, IOException {
        // 1. Encode the UUID into a QR Code bit matrix
        BitMatrix bitMatrix = qrCodeWriter.encode(
                uniqueId.toString(),
                BarcodeFormat.QR_CODE,
                QR_WIDTH,
                QR_HEIGHT
        );

        // 2. Convert the bit matrix to a BufferedImage
        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // 3. Write BufferedImage into a byte[] (PNG format)
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(qrCodeImage, "PNG", baos);
            return baos.toByteArray(); // ✅ actual PNG bytes, safe to store in BYTEA
        }
    }


    @Override
    public QrCode generateQrCode(Ticket ticket) {
        try {
            UUID uniqueId = UUID.randomUUID();
            byte[] qrCodeImage = generateImage(uniqueId);

            QrCode qrCode = new QrCode();
            qrCode.setId(uniqueId);
            qrCode.setStatus(QrCodeStatusEnum.ACTIVE);
            log.info("Type of qrCodeImage: {}", qrCodeImage.getClass().getName());
            log.info("Length of qrCodeImage: {}", qrCodeImage.length);
//            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            qrCode.setValue( qrCodeImage);
            qrCode.setTicket(ticket);

            log.info("Generated QR Code (Base64): {}", Base64.getEncoder().encodeToString(qrCodeImage));

            return qrCodeRepository.saveAndFlush(qrCode);
        } catch (IOException | WriterException ex) {
            throw new QrCodeGenerationException("Unable to create QR Code", ex);
        }
    }


    @Override
    public byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId) {
        QrCode qrCode = qrCodeRepository.findByTicketIdAndTicketPurchaserId(ticketId, userId)
                .orElseThrow(QrCodeNotFoundException::new);

        return qrCode.getValue();
    }

    @Override
    public String getQrCodeBase64(UUID userId, UUID ticketId) {
        QrCode qrCode = qrCodeRepository.findByTicketIdAndTicketPurchaserId(ticketId, userId)
                .orElseThrow(QrCodeNotFoundException::new);

        return Base64.getEncoder().encodeToString(qrCode.getValue());
    }

}
