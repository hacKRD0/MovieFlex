package com.movie.ticket.booking.system.payment.service.kafka.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.ticket.booking.system.payment.service.dtos.BookingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceKafkaPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void pushBookingDetailsToPaymentResponseTopic(BookingDTO bookingDTO) {
        log.info("Publishing booking details {} to payment-responsee topic", bookingDTO.toString());
        try {
            this.kafkaTemplate.send("payment-response", objectMapper.writeValueAsString(bookingDTO));
        } catch (Exception e) {
            log.error("Error while publishing booking details {} to payment-response topic", bookingDTO.toString());
        }
    }
}
