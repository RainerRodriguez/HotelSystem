package com.hotelsystem.controller;
import com.hotelsystem.dto.UserCreationDTO;
import com.hotelsystem.dto.UserResponseDTO;
import com.hotelsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,@RequestBody @Valid UserCreationDTO userCreationDTO){
        return userService.updateUser(id, userCreationDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    //Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }




}
