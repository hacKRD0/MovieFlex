package com.movie.booking.system.apigateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingAPIHandlerCallback {
    @GetMapping("/booking-fallback")
    public String bookingApiFallback(){
        return "Booking service is is in maintenance mode. Please try after some time...";
    }
}
