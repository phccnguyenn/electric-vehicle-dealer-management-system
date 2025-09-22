package com.evdealer.ev_dealer_management.auth.controller;

import com.evdealer.ev_dealer_management.auth.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login() {
        // This is a placeholder implementation.
        // In a real application, you would handle the login logic here.
        AuthResponse response = new AuthResponse("dummy-token", "USER");
        return ResponseEntity.ok(response);
    }
}
