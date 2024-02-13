package com.movie.ticket.booking.system.payment.service.services;

import com.movie.ticket.booking.system.payment.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.payment.service.dtos.BookingStatus;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StripePaymentGateway {

    @Value("${stripe.key}")
    private String secretKey;

    @PostConstruct
    public void init(){
        Stripe.apiKey = secretKey;
    }

    public BookingDTO makePayment(BookingDTO bookingDTO)  {
        log.info("Entered into StripePaymentGateway with the request data : "+ bookingDTO.toString());
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int)Math.round(bookingDTO.getBookingAmount()*100));
        chargeParams.put("currency", "inr");
        chargeParams.put("description", "Test Payment From Payment Service");
        chargeParams.put("source", "tok_amex");
        try {
            Charge.create(chargeParams); // doing payment online in test mode
            log.info("Payment was successful for the booking id {}", bookingDTO.getBookingId());
            bookingDTO.setBookingStatus(BookingStatus.CONFIRMED);
        } catch (StripeException e) {
            log.error("Stripe payment failed. See the exception details for more : "+ e.getMessage());
            bookingDTO.setBookingStatus(BookingStatus.CANCELLED);
        }
        return bookingDTO;
    }

}
