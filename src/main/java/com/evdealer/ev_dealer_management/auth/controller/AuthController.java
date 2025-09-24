package com.evdealer.ev_dealer_management.auth.controller;

import com.evdealer.ev_dealer_management.auth.model.dto.AuthRequest;
import com.evdealer.ev_dealer_management.auth.model.dto.AuthResponse;
import com.evdealer.ev_dealer_management.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    /* @PostMapping("/admin/register")
    public ResponseEntity<AuthResponse> register(
            @ResponseBody AuthRequest authRequest
    ) {
        return ResponseEntity.ok(authService.registerUser(authRequest));
    }
    */
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }
}
