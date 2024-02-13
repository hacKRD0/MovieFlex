package com.movie.ticket.booking.system.service.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.ticket.booking.system.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.service.services.impl.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceKafkaListener {

    private final BookingService bookingService;
    private final ObjectMapper objectMapper;

    public void pullBookingDetailsFromPaymentResponseTopic(String bookingsJson) {
        log.info("Received booking details {} from the payment response topic.", bookingsJson);
        try{
            this.bookingService.processBooking(objectMapper.readValue(bookingsJson, BookingDTO.class));
        } catch (Exception e) {
            log.error("Error while receiving booking details {} from the payment-response topic", bookingsJson);
        }
    }
}
