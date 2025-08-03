package com.hotelsystem.dto;

public class LoginResponseDTO {
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public LoginResponseDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
