package com.movie.ticket.booking.system.payment.service.services.impl;

import com.movie.ticket.booking.system.payment.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.payment.service.dtos.BookingStatus;
import com.movie.ticket.booking.system.payment.service.entities.PaymentEntity;
import com.movie.ticket.booking.system.payment.service.entities.PaymentStatus;
import com.movie.ticket.booking.system.payment.service.kafka.listener.PaymentServiceKafkaListener;
import com.movie.ticket.booking.system.payment.service.kafka.publisher.PaymentServiceKafkaPublisher;
import com.movie.ticket.booking.system.payment.service.repositories.PaymentRepository;
import com.movie.ticket.booking.system.payment.service.services.PaymentService;
import com.movie.ticket.booking.system.payment.service.services.StripePaymentGateway;
import com.stripe.exception.StripeException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StripePaymentGateway stripePaymentGateway;
    private final PaymentServiceKafkaPublisher paymentServiceKafkaPublisher;

    @Override
    @Transactional
    public void processPayment(BookingDTO bookingDTO) throws StripeException {
        log.info("Entered into PaymentServiceImpl with payment amount {} for the booking " +
                "id {}", bookingDTO.getBookingAmount(), bookingDTO.getBookingId());
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .paymentStatus(PaymentStatus.PENDING)
                .paymentAmount(bookingDTO.getBookingAmount())
                .bookingId(bookingDTO.getBookingId())
                .build();
        this.paymentRepository.save(paymentEntity);
        bookingDTO = this.stripePaymentGateway.makePayment(bookingDTO);
        paymentEntity.setPaymentTimestamp(LocalDateTime.now());
        if (bookingDTO.getBookingStatus().equals(BookingStatus.CONFIRMED)) {
            paymentEntity.setPaymentStatus(PaymentStatus.APPROVED);
        }else{
            paymentEntity.setPaymentStatus(PaymentStatus.FAILED);
        }
    this.paymentServiceKafkaPublisher.pushBookingDetailsToPaymentResponseTopic(bookingDTO);
    }
}
