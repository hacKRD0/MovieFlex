//package com.movie.ticket.booking.system.payment.service.apis;
//
//import com.movie.ticket.booking.system.payment.service.dtos.BookingDTO;
//import com.movie.ticket.booking.system.payment.service.services.PaymentService;
//import com.stripe.exception.StripeException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("payments")
//@Slf4j
//public class PaymentAPI {
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @PostMapping
//    public BookingDTO processPayment(@RequestBody BookingDTO bookingDTO) throws StripeException {
//        log.info("Entered into PaymentAPI with the request data {} ", bookingDTO.toString());
//        return this.paymentService.processPayment(bookingDTO);
//    }
//}
