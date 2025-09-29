package com.evdealer.ev_dealer_management.auth.controller;

import com.evdealer.ev_dealer_management.auth.model.User;
import com.evdealer.ev_dealer_management.auth.model.dto.AuthRequest;
import com.evdealer.ev_dealer_management.auth.model.dto.AuthResponse;
import com.evdealer.ev_dealer_management.auth.model.dto.RegisterRequest;
import com.evdealer.ev_dealer_management.auth.model.dto.RegisterResponse;
import com.evdealer.ev_dealer_management.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest authRequest
    ) {
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest user) {
        RegisterResponse response = authService.register(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

}
