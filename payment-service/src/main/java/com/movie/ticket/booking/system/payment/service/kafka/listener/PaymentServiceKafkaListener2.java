package com.movie.ticket.booking.system.payment.service.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentServiceKafkaListener2 {

    @KafkaListener(topics = "payment-request", groupId = "payment-request-group-2")
    public void pullBookingDetailsFromPaymentRequestTopic(String bookingJson) {
        log.info("Received booking details {} from payment-request topic", bookingJson);
    }
}
