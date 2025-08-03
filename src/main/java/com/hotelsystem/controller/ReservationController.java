package com.hotelsystem.controller;

import com.hotelsystem.dto.ReserveRequestDTO;
import com.hotelsystem.dto.ReserveResponseDTO;
import com.hotelsystem.model.enums.PaymentMethod;
import com.hotelsystem.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    // Create a reservation
    @PostMapping("/create")
    public ResponseEntity<ReserveResponseDTO> createReservation(@Valid @RequestBody ReserveRequestDTO reserveRequestDTO){
        return ResponseEntity.ok(reservationService.createReservation(reserveRequestDTO));
    }

    //Cancel reservation payment
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    //List reservations
    @GetMapping()
    public ResponseEntity<List<ReserveResponseDTO>> getAllReservations(){
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    //Get reservation by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReserveResponseDTO>> getReservationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUser(userId));
    }

    // Pay reservation
    @PostMapping("/{id}/pay")
    public ResponseEntity<ReserveResponseDTO> payReservation(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestParam PaymentMethod method
    ) {
        ReserveResponseDTO responseDTO = reservationService.payReservation(id, userId, method);
        return ResponseEntity.ok(responseDTO);
    }

    //Delete reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
