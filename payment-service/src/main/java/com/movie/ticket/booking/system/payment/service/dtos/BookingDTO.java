package com.movie.ticket.booking.system.payment.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {
    private UUID bookingId;
    private String userId;
    private Integer movieId;
    private List<String> seatsSelected;
    private LocalDate showDate;
    private LocalTime showTime;
    private Double bookingAmount;
    private BookingStatus bookingStatus;
}
