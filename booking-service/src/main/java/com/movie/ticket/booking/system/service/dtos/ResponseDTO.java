package com.movie.ticket.booking.system.service.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private List<String> errorMessages;// jakarta validation constraint exceptions
    private String errorDescription;// exceptions which are raising from code
    private String statusCodeDescription;
    private LocalDateTime timestamp;
    private BookingDTO bookingDetails;
}
