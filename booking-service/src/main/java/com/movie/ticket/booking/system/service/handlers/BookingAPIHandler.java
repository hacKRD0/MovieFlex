package com.movie.ticket.booking.system.service.handlers;

import com.movie.ticket.booking.system.service.dtos.ResponseDTO;
import com.movie.ticket.booking.system.service.exceptions.BookingsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BookingAPIHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseDTO> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        return new ResponseEntity<>(ResponseDTO.builder()
                .statusCodeDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errorMessages(
                        methodArgumentNotValidException.getBindingResult().getAllErrors()
                                .stream()
                                .map(ObjectError::getDefaultMessage)
                                .collect(Collectors.toList())
                )
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingsException.class)
    public ResponseEntity<ResponseDTO> runtimeException(BookingsException bookingsException){
        return new ResponseEntity<ResponseDTO>(
                ResponseDTO.builder()
                        .errorDescription(bookingsException.getMessage())
                        .statusCodeDescription(HttpStatus.OK.getReasonPhrase())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.OK);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO> runtimeException(RuntimeException runtimeException){
        return new ResponseEntity<ResponseDTO>(
                ResponseDTO.builder()
                        .errorDescription(runtimeException.getMessage())
                        .statusCodeDescription(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
