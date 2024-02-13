package com.movie.ticket.booking.system.payment.service.services;

import com.movie.ticket.booking.system.payment.service.dtos.BookingDTO;
import com.stripe.exception.StripeException;

public interface PaymentService {

    public void processPayment(BookingDTO bookingDTO) throws StripeException;
}
