package com.hotelsystem.service;

import com.hotelsystem.dto.ReserveRequestDTO;
import com.hotelsystem.dto.ReserveResponseDTO;
import com.hotelsystem.mappers.Mapper;
import com.hotelsystem.model.Payment;
import com.hotelsystem.model.Reservation;
import com.hotelsystem.model.Room;
import com.hotelsystem.model.User;
import com.hotelsystem.model.enums.PaymentMethod;
import com.hotelsystem.model.enums.PaymentStatus;
import com.hotelsystem.repository.PaymentRepository;
import com.hotelsystem.repository.ReservationRepository;
import com.hotelsystem.repository.RoomRepository;
import com.hotelsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final Mapper mapper;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(Mapper mapper, RoomRepository roomRepository, UserRepository userRepository,
                              ReservationRepository reservationRepository, PaymentRepository paymentRepository) {
        this.mapper = mapper;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.paymentRepository = paymentRepository;
    }

    // Create reservation
    public ReserveResponseDTO createReservation(ReserveRequestDTO dto) {

        // Get the user
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User with id: " + dto.getUserId() + " not found"));

        // Get room
        Room room = roomRepository.findById(dto.getRoomId()).orElseThrow(()-> new RuntimeException("Room with id: " + dto.getRoomId() + " not found"));

        // Calculate days
        long nights = ChronoUnit.DAYS.between(dto.getCheckIn(), dto.getCheckOut());
        if (nights < 0){
            throw new RuntimeException("Check-out has to be at least 1 night after Check-in");
        }

        //Validate if the room is occupied those dates
        boolean roomOccupied = reservationRepository.existsByRoomAndDatesOverlap(
                room.getId(),
                dto.getCheckIn(),
                dto.getCheckOut()
        );

        if (roomOccupied){
            throw new RuntimeException("The room is reserved already for those dates");
        }

        // Map reservation to dto
        Reservation reservation = mapper.reservationToDto(dto);

        // Set room and user to the reservation
        reservation.setRoom(room);
        reservation.setUser(user);
        reservation.setPaymentStatus(PaymentStatus.PENDING);

        // Save reservation
        Reservation reservationSaved = reservationRepository.save(reservation);


        // Calculate total amount
        BigDecimal totalAmount = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        ReserveResponseDTO responseDTO = mapper.dtoToReservation(reservationSaved);

        // The frontend will see the total amount
        responseDTO.setTotalAmount(totalAmount);

        return responseDTO;
    }

    // Cancel reservation payment status
    public void cancelReservation(Long id){
        Reservation reservation = reservationRepository.findById(id).orElseThrow(()-> new RuntimeException("The reservation with id: " + id + " want found."));

        if (reservation.getPaymentStatus() == PaymentStatus.CANCELLED){
            throw  new RuntimeException("Reserve is already cancelled");
        }

        if (reservation.getPaymentStatus() == PaymentStatus.CONFIRMED){
            throw new RuntimeException("Cannot cancel Paid reservation, contact us for a refund");
        }

        reservation.setPaymentStatus(PaymentStatus.CANCELLED);

        reservationRepository.save(reservation);
    }

    // Get all reservations
    public List<ReserveResponseDTO> getAllReservations(){

        List<Reservation> reservations = reservationRepository.findAll();

        List<ReserveResponseDTO> list = reservations.stream().
                map(mapper::dtoToReservation).collect(Collectors.toList());
        return list;
    }

    // Get reservation by user id
    public List<ReserveResponseDTO> getReservationsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        List<Reservation> reservations = reservationRepository.findByUser(user);

        return reservations.stream()
                .map(mapper::dtoToReservation)
                .collect(Collectors.toList());
    }


    // Pay reservation
    public ReserveResponseDTO payReservation(Long reservationId, Long userId, PaymentMethod method) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (!reservation.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to pay this reservation.");
        }

        if (reservation.getPaymentStatus() == PaymentStatus.CONFIRMED) {
            throw new RuntimeException("Reservation is already paid");
        }

        if (reservation.getPaymentStatus() == PaymentStatus.CANCELLED) {
            throw new RuntimeException("Cannot pay a cancelled reservation");
        }

        Payment payment = new Payment();
        // Calculate the total amount of the reservation
        payment.setAmount(reservation.getRoom().getPricePerNight()
                .multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(reservation.getCheckIn(), reservation.getCheckOut()))));
        payment.setMethod(method);
        payment.setPayDate(LocalDate.now());
        payment.setReservation(reservation);

        paymentRepository.save(payment);

        reservation.setPayment(payment);
        reservation.setPaymentStatus(PaymentStatus.CONFIRMED);
        reservationRepository.save(reservation);

        return mapper.dtoToReservation(reservation);
    }

    // Delete reservation
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation with id " + id + " not found."));

        if (reservation.getPaymentStatus() == PaymentStatus.CONFIRMED) {
            throw new RuntimeException("Cannot delete a paid reservation. Please request cancellation or contact us.");
        }

        // If there is a payment delete it first
        Payment payment = reservation.getPayment();
        if (payment != null) {
            paymentRepository.delete(payment);
        }

        reservationRepository.delete(reservation);
    }

}
