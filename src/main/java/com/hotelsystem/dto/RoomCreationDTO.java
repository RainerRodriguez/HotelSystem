package com.hotelsystem.dto;

import com.hotelsystem.model.enums.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class RoomCreationDTO {

    @NotNull(message = "Room type is required")
    private RoomType type;
    @NotBlank(message = "Room number is required")
    private String roomNumber;
    @NotNull(message = "Price is required")
    private BigDecimal pricePerNight;

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
}
