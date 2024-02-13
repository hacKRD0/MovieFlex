package com.movie.ticket.booking.system.payment.service.repositories;

import com.movie.ticket.booking.system.payment.service.entities.PaymentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PaymentRepository extends CrudRepository<PaymentEntity, UUID> {
}
