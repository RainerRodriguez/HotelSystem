package com.hotelsystem.service;

import com.hotelsystem.dto.LoginDTO;
import com.hotelsystem.dto.LoginResponseDTO;
import com.hotelsystem.dto.UserCreationDTO;
import com.hotelsystem.dto.UserResponseDTO;
import com.hotelsystem.mappers.Mapper;
import com.hotelsystem.model.User;
import com.hotelsystem.model.enums.UserRole;
import com.hotelsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(Mapper mapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // Method to create user
    public User createGuest(UserCreationDTO dto){
        if (userRepository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("The email already exist");
        }

        //map the DTO to the user
        User user = mapper.ToUser(dto);
        //hash the password
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        //set the roll of creation to Guest
        user.setRole(UserRole.GUEST);
        // save the user
        return userRepository.save(user);
    }

    // Method to login
    public LoginResponseDTO login(LoginDTO loginDTO){

        // Check if the user exist by email
        User user = userRepository.findByEmail(loginDTO.getEmail()).
                orElseThrow(()-> new RuntimeException("User dont exist"));

        // Check if the encoded password mach the password of the user
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        return new LoginResponseDTO("login successfully", user.getEmail());
    }

    // List of users
    public List<UserResponseDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        // map every user in the list to dto
        return users.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    // Get user by id
    public Optional<UserResponseDTO> getUserById(Long id){
        return userRepository.findById(id).map(mapper::toDto);
    }

    // Update user
    public Optional<UserResponseDTO> updateUser(Long id, UserCreationDTO userToUpdate) {

        User user = userRepository.findById(id).
                orElseThrow(() -> new RuntimeException("User with id: " + id + " not found."));

        user.setName(userToUpdate.getName());
        user.setLastName(userToUpdate.getLastName());
        user.setEmail(userToUpdate.getEmail());
        user.setPhone(userToUpdate.getPhone());

        String encodedPassword = passwordEncoder.encode(userToUpdate.getPassword());
        user.setPassword(encodedPassword);

        User userUpdated = userRepository.save(user);
        return Optional.of(mapper.toDto(userUpdated));

    }

    // Delete user
    public void deleteUser(Long id){
        User user = userRepository.findById(id).
                orElseThrow(()-> new RuntimeException("User with " + id + " not found"));

        userRepository.delete(user);

    }

}






