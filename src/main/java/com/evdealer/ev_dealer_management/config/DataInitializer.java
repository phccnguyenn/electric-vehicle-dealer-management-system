package com.evdealer.ev_dealer_management.config;

import com.evdealer.ev_dealer_management.auth.model.dto.RegisterRequest;
import com.evdealer.ev_dealer_management.auth.model.dto.RegisterResponse;
import com.evdealer.ev_dealer_management.auth.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.auth.service.AuthService;
import com.evdealer.ev_dealer_management.common.exception.DuplicatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final AuthService authService;

    public DataInitializer(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void run(String... args) throws Exception {

        String username = "evd.admin";
        String password = "Evd@2025!";

        RegisterRequest request = RegisterRequest.builder()
                .username("evd.admin")
                .password(password)
                .fullName("EVD Administrator")
                .email("admin@evdcompany.com")
                .phone("0987654321")
                .isActive(true)
                .role(RoleType.EVM_ADMIN)
                .build();

        try {
            RegisterResponse response = authService.register(request);
            log.info("[LOG] - Admin count with username {}", username);
        } catch (DuplicatedException e) {
            throw new RuntimeException(e);
        }

    }
}
