package com.movie.ticket.booking.system.service.services.impl;

import com.movie.ticket.booking.system.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.service.exceptions.BookingsException;

import java.util.UUID;

public interface BookingService {
    public BookingDTO createBooking(BookingDTO bookingDTO);

    public BookingDTO getBookingDetails(UUID bookingId) throws BookingsException;

    public void processBooking(BookingDTO bookingDTO) throws BookingsException;
}
