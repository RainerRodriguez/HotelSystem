package com.hotelsystem.dto;

import com.hotelsystem.model.enums.RoomType;


import java.math.BigDecimal;

public class RoomResponseDTO {
    private Long id;
    private RoomType type;
    private String roomNumber;
    private BigDecimal pricePerNight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
