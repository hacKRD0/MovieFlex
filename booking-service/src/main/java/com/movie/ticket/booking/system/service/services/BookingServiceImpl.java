package com.movie.ticket.booking.system.service.services;

import com.movie.ticket.booking.system.service.brokers.PaymentServiceBroker;
import com.movie.ticket.booking.system.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.service.dtos.BookingStatus;
import com.movie.ticket.booking.system.service.exceptions.BookingsException;
import com.movie.ticket.booking.system.service.kafka.publisher.BookingServiceKafkaPublisher;
import com.movie.ticket.booking.system.service.services.impl.BookingService;
import com.movie.ticket.booking.system.service.entities.BookingEntity;
import com.movie.ticket.booking.system.service.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingServiceKafkaPublisher kafkaPublisher;
    private final PaymentServiceBroker paymentServiceBroker;

    @Override
    @Transactional
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        log.info("Entered into BookingServiceImpl class with request : " + bookingDTO.toString());
        BookingEntity bookingEntity = BookingEntity.builder()
                .bookingAmount(bookingDTO.getBookingAmount())
                .seatsSelected(bookingDTO.getSeatsSelected())
                .bookingStatus(BookingStatus.PENDING)
                .movieId(bookingDTO.getMovieId())
                .userId(bookingDTO.getUserId())
                .showDate(bookingDTO.getShowDate())
                .showTime(bookingDTO.getShowTime())
                .build();
        this.bookingRepository.save(bookingEntity); // create a booking with booking status as PENDING
        bookingDTO.setBookingId(bookingEntity.getBookingId());
        bookingDTO.setBookingStatus(BookingStatus.PENDING);
        //Publish to kafka topic
        this.kafkaPublisher.publishBookingDetailsToPaymentRequestTopic(bookingDTO);
        return bookingDTO;
    }

    @Override
    @Transactional
    public BookingDTO getBookingDetails(UUID bookingID) throws BookingsException {
        BookingEntity bookingEntity = this.bookingRepository
                        .findById(bookingID)
                        .orElseThrow(() -> new BookingsException("No Booking details found with booking id "+ bookingID));
        return BookingDTO.builder()
                .bookingId(bookingEntity.getBookingId())
                .bookingAmount(bookingEntity.getBookingAmount())
                .bookingStatus(bookingEntity.getBookingStatus())
                .movieId(bookingEntity.getMovieId())
                .showTime(bookingEntity.getShowTime())
                .showDate(bookingEntity.getShowDate())
                .bookingAmount(bookingEntity.getBookingAmount())
                .userId(bookingEntity.getUserId())
                .seatsSelected(bookingEntity.getSeatsSelected())
                .build();
    }

    @Override
    @Transactional
    public void processBooking(BookingDTO bookingDTO) throws BookingsException {
        BookingEntity bookingEntity = this.bookingRepository
                .findById(bookingDTO.getBookingId())
                .orElseThrow(() -> new BookingsException("No Booking details found with booking id "+ bookingDTO.getBookingId()));
        bookingEntity.setBookingStatus(bookingDTO.getBookingStatus());
    }
}
