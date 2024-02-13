package com.movie.ticket.booking.system.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.ticket.booking.system.services.JavaMailSenderNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceKafkaListener {

    private final ObjectMapper objectMapper;
    private final JavaMailSenderNotification javaMailSenderNotification;

    @KafkaListener(topics = "payment-response", groupId = "payment-response-group-2")
    public void pullBookingDetailsFromPaymentResponseTopic(String bookingsJson){
        log.info("Received booking details {} from the payment response topic", bookingsJson);
        try{
            this.javaMailSenderNotification.sendMail(bookingsJson);
        }catch(Exception e){
            log.error("Error while receiving booking details from the payment response topic");
        }
    }
}
