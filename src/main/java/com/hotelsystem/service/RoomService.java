package com.hotelsystem.service;

import com.hotelsystem.dto.RoomCreationDTO;
import com.hotelsystem.dto.RoomResponseDTO;
import com.hotelsystem.mappers.Mapper;
import com.hotelsystem.model.Room;
import com.hotelsystem.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final Mapper mapper;

    public RoomService(RoomRepository roomRepository, Mapper mapper) {
        this.roomRepository = roomRepository;
        this.mapper = mapper;
    }

    //Create a room
    public Room createRoom(RoomCreationDTO dto){
        if (roomRepository.existsByRoomNumber(dto.getRoomNumber())){
            throw new IllegalArgumentException("The Room with the number " + dto.getRoomNumber() + " already exist");
        }

        Room room = mapper.dtoToRoom(dto);

        return roomRepository.save(room);
    }

    // List of rooms
    public List<RoomResponseDTO> getAllRooms(){
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(mapper::roomToDto).collect(Collectors.toList());
    }

    // Get room by id;
    public Optional<RoomResponseDTO> getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(mapper::roomToDto);
    }

    // Update room
    public Optional<RoomResponseDTO> updateRoom(Long id, RoomCreationDTO dto){
        Room room = roomRepository.findById(id).orElseThrow(()-> new RuntimeException("Room with id: " + id + " not found"));

        // update the room data.
        room.setRoomNumber(dto.getRoomNumber());
        room.setPricePerNight(dto.getPricePerNight());
        room.setType(dto.getType());

        Room roomUpdated = roomRepository.save(room);
        return Optional.of(mapper.roomToDto(roomUpdated));
    }

    // Delete room
    public void deleteRoom(Long id){
        Room room = roomRepository.findById(id).orElseThrow(()-> new RuntimeException("Room with id " + id + " not found"));

        roomRepository.delete(room);
    }




}
