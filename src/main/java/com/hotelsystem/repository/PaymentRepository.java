package com.hotelsystem.repository;

import com.hotelsystem.model.Payment;
import com.hotelsystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByReservation(Reservation reservation);
}
