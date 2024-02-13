package com.movie.ticket.booking.system.payment.service.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.ticket.booking.system.payment.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.payment.service.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceKafkaListener {

    private final ObjectMapper objectMapper;
    private final PaymentService paymentService;

    @KafkaListener(topics = "payment-request", groupId = "payment-request-group-1")
    public void pullBookingDetailsFromPaymentRequestTopic(String bookingsJson) {
        log.info("Received booking details {} from payment-request topic", bookingsJson);
        try {
            BookingDTO bookingDTO = objectMapper.readValue(bookingsJson, BookingDTO.class);
            this.paymentService.processPayment(bookingDTO);
        } catch (Exception e) {
            log.error("Error while receiving booking details from the payment-request topic in payment-service");
        }
    }
}
