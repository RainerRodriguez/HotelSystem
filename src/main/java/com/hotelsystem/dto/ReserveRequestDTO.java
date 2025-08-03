package com.hotelsystem.dto;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReserveRequestDTO {

    @NotNull(message = "Check-In is required")
    private LocalDate checkIn;
    @NotNull(message = "Check-Out is required")
    private LocalDate checkOut;
    @NotNull(message = "The user id must be valid")
    private Long userId;
    @NotNull(message = "the room id must be valid")
    private Long roomId;

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
