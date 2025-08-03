package com.hotelsystem.mappers;

import com.hotelsystem.dto.*;
import com.hotelsystem.model.Payment;
import com.hotelsystem.model.Reservation;
import com.hotelsystem.model.Room;
import com.hotelsystem.model.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class Mapper {

    // Map user to dto
    public User ToUser(UserCreationDTO dto){
        if (dto == null) return null;

        User user = new User();
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        //password will be set in service layer
        return user;
    }

    // Map dto to user
    public UserResponseDTO toDto(User user){
        if (user == null) return null;

        UserResponseDTO dto = new UserResponseDTO();

        dto.setName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());

        return dto;
    }

    //Map dto to room
    public Room dtoToRoom(RoomCreationDTO dto){
        if (dto == null) return null;

        Room room = new Room();
        room.setType(dto.getType());
        room.setRoomNumber(dto.getRoomNumber());
        room.setPricePerNight(dto.getPricePerNight());


        return room;
    }

    // Map room to dto
    public RoomResponseDTO roomToDto(Room room){
        if (room == null) return null;

        RoomResponseDTO dto = new RoomResponseDTO();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setPricePerNight(room.getPricePerNight());
        dto.setType(room.getType());

        return dto;
    }

    // Map Reservation to dto;
    public Reservation reservationToDto(ReserveRequestDTO dto){
        if (dto == null) return null;

        Reservation reservation = new Reservation();

        reservation.setCheckIn(dto.getCheckIn());
        reservation.setCheckOut(dto.getCheckOut());

        return reservation;

    }




    // Map dto to Reservation
    public ReserveResponseDTO dtoToReservation(Reservation reservation){
        if (reservation == null) return  null;

        ReserveResponseDTO responseDTO = new ReserveResponseDTO();

        responseDTO.setId(reservation.getId());
        responseDTO.setCheckIn(reservation.getCheckIn());
        responseDTO.setCheckOut(reservation.getCheckOut());
        responseDTO.setRoomNumber(reservation.getRoom().getRoomNumber());
        responseDTO.setUserName(reservation.getUser().getName());
        responseDTO.setUserLastname(reservation.getUser().getLastName());
        responseDTO.setPricePerNight(reservation.getRoom().getPricePerNight());
        responseDTO.setPaymentStatus(reservation.getPaymentStatus());
        responseDTO.setRoomType(reservation.getRoom().getType());

        // Calculate totalAmount
        long nights = ChronoUnit.DAYS.between(reservation.getCheckIn(), reservation.getCheckOut());
        if (nights > 0) {
            BigDecimal total = reservation.getRoom().getPricePerNight().multiply(BigDecimal.valueOf(nights));
            responseDTO.setTotalAmount(total);
        } else {
            responseDTO.setTotalAmount(BigDecimal.ZERO);
        }

        return responseDTO;
    }


    public Payment dtoToPayment(PaymentRequestDTO dto, Reservation reservation) {
        if (dto == null || reservation == null) return null;

        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setMethod(dto.getMethod());
        payment.setPayDate(LocalDate.now());
        payment.setReservation(reservation);

        return payment;
    }

    public PaymentResponseDTO paymentToDto(Payment payment) {
        if (payment == null) return null;

        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setMethod(payment.getMethod());
        dto.setPayDate(payment.getPayDate());
        dto.setReservationId(payment.getReservation().getId());

        return dto;
    }
}
