package com.hotelsystem.controller;

import com.hotelsystem.dto.LoginDTO;
import com.hotelsystem.dto.LoginResponseDTO;
import com.hotelsystem.dto.UserCreationDTO;
import com.hotelsystem.dto.UserResponseDTO;
import com.hotelsystem.mappers.Mapper;
import com.hotelsystem.model.User;
import com.hotelsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final Mapper mapper;

    public AuthController(UserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    // Create user (Guest)
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createGuest(@Valid @RequestBody UserCreationDTO dto){
        // create the user
        User user = userService.createGuest(dto);
        // turn the response into dto
        UserResponseDTO  responseDto = mapper.toDto(user);
        return ResponseEntity.ok(responseDto);
    }

    // Login with email and password
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO){
        LoginResponseDTO responseDTO = userService.login(loginDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
