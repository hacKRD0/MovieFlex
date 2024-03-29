package com.movie.ticket.booking.system.service.brokers;

import com.movie.ticket.booking.system.service.dtos.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url="http://payment-service:9091/payments")
public interface PaymentServiceBroker {

    @PostMapping
    public BookingDTO processPayment(@RequestBody BookingDTO bookingDTO);

}
