package com.hotelsystem.service;

import com.hotelsystem.dto.RoomCreationDTO;
import com.hotelsystem.mappers.Mapper;
import com.hotelsystem.model.Room;
import com.hotelsystem.model.enums.RoomType;
import com.hotelsystem.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateRoomSuccessfully() {
        // mock data
        RoomCreationDTO dto = new RoomCreationDTO();
        dto.setRoomNumber("101");
        dto.setPricePerNight(BigDecimal.valueOf(120.0));
        dto.setType(RoomType.BASIC);

        Room room = new Room();
        room.setRoomNumber("101");
        room.setPricePerNight(BigDecimal.valueOf(120.00));
        room.setType(RoomType.BASIC);

        // Mocks behavior
        when(roomRepository.existsByRoomNumber("101")).thenReturn(false);
        when(mapper.dtoToRoom(dto)).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(room);

        // method to test
        Room result = roomService.createRoom(dto);

        // verify results
        assertNotNull(result);
        assertEquals("101", result.getRoomNumber());
        verify(roomRepository, times(1)).save(room);
    }


    @Test
    void shouldThrowExceptionWhenRoomAlreadyExists() {

        RoomCreationDTO dto = new RoomCreationDTO();
        dto.setRoomNumber("101");

        when(roomRepository.existsByRoomNumber("101")).thenReturn(true);


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> roomService.createRoom(dto)
        );

        assertEquals("The Room with the number 101 already exist", exception.getMessage());
        verify(roomRepository, never()).save(any());
    }

}
