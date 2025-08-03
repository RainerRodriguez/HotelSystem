package com.hotelsystem.service;

import com.hotelsystem.dto.PaymentRequestDTO;
import com.hotelsystem.dto.PaymentResponseDTO;
import com.hotelsystem.mappers.Mapper;
import com.hotelsystem.model.Payment;
import com.hotelsystem.model.Reservation;
import com.hotelsystem.model.enums.PaymentStatus;
import com.hotelsystem.repository.PaymentRepository;
import com.hotelsystem.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final Mapper mapper;

    public PaymentService(PaymentRepository paymentRepository, ReservationRepository reservationRepository, Mapper mapper) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.mapper = mapper;
    }

    public PaymentResponseDTO createPayment(PaymentRequestDTO requestDTO) {
        Reservation reservation = reservationRepository.findById(requestDTO.getReservationId())
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + requestDTO.getReservationId()));

        if (reservation.getPaymentStatus() == PaymentStatus.CONFIRMED) {
            throw new RuntimeException("This reservation is already paid");
        }

        if (reservation.getPaymentStatus() == PaymentStatus.CANCELLED) {
            throw new RuntimeException("Cannot pay for a cancelled reservation");
        }

        Payment payment = mapper.dtoToPayment(requestDTO, reservation);
        paymentRepository.save(payment);

        reservation.setPayment(payment);
        reservation.setPaymentStatus(PaymentStatus.CONFIRMED);
        reservationRepository.save(reservation);

        return mapper.paymentToDto(payment);
    }

    public PaymentResponseDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        return mapper.paymentToDto(payment);
    }

    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(mapper::paymentToDto)
                .collect(Collectors.toList());
    }

    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment with ID " + id + " not found."));

        Reservation reservation = payment.getReservation();
        if (reservation != null) {
            reservation.setPayment(null);
            reservation.setPaymentStatus(PaymentStatus.PENDING);
            reservationRepository.save(reservation);
        }

        paymentRepository.delete(payment);
    }
}
