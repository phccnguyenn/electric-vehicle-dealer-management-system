package com.evdealer.ev_dealer_management.auth.entity.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LoginResponse {
    private String username;
    private String fullName;
    private LocalDateTime loginTime; //Used for logging purpose
    private String token;
    private String role;

    public LoginResponse(String username, String fullName, String token, String role) {
        this.username = username;
        this.fullName = fullName;
        this.token = token;
        this.role = role;
        this.loginTime = LocalDateTime.now();
    }
}
