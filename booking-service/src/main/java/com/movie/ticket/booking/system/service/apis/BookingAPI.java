package com.movie.ticket.booking.system.service.apis;

import com.movie.ticket.booking.system.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.service.exceptions.BookingsException;
import com.movie.ticket.booking.system.service.services.impl.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping ("/bookings")
@Slf4j
@AllArgsConstructor
public class BookingAPI {

    private BookingService bookingService;

    private Environment environment;

    @PostMapping
    public BookingDTO createBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        log.info("Entered into BookingApi with details : "+ bookingDTO.toString());
        return this.bookingService.createBooking(bookingDTO);
    }

    @GetMapping("/{traking_id}")
    public BookingDTO getBookingDetails(@PathVariable UUID booking_id) throws BookingsException {
        return this.bookingService.getBookingDetails(booking_id);
    }
}
