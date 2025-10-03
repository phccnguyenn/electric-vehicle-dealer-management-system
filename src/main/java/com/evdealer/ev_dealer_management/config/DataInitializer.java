package com.evdealer.ev_dealer_management.config;

import com.evdealer.ev_dealer_management.auth.model.dto.RegisterRequest;
import com.evdealer.ev_dealer_management.auth.model.dto.RegisterResponse;
import com.evdealer.ev_dealer_management.auth.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.auth.service.AuthService;
import com.evdealer.ev_dealer_management.common.exception.DuplicatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final AuthService authService;

    public DataInitializer(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void run(String... args) throws Exception {

        RegisterRequest request = RegisterRequest.builder()
                .username("evd.admin")
                .password("Evd@2025!")
                .fullName("EVD Administrator")
                .email("admin@evdcompany.com")
                .phone("0987654321")
                .isActive(true)
                .role(RoleType.EVM_ADMIN)
                .build();

        List<RegisterRequest> requests = new ArrayList<>();
        requests.add(RegisterRequest.builder()
                .username("evd.admin")
                .password("00000")
                .fullName("EVD Administrator")
                .email("admin@evdcompany.com")
                .phone("0987654321")
                .isActive(true)
                .role(RoleType.EVM_ADMIN)
                .build());

        requests.add(RegisterRequest.builder()
                .username("evd.staff001")
                .password("00000")
                .fullName("EVD Staff")
                .email("staff001@evdcompany.com")
                .phone("0986000976")
                .isActive(true)
                .role(RoleType.EVM_STAFF)
                .build());

        requests.add(RegisterRequest.builder()
                .username("dealer.manager001")
                .password("00000")
                .fullName("Dealer Manager")
                .email("dealer_manager001@evdcompany.com")
                .phone("0986194321")
                .isActive(true)
                .role(RoleType.DEALER_MANAGER)
                .build());

        requests.add(RegisterRequest.builder()
                .username("dealer.staff001")
                .password("00000")
                .fullName("Dealer Staff")
                .email("dealer_staff001@evdcompany.com")
                .phone("0916951021")
                .isActive(true)
                .role(RoleType.DEALER_STAFF)
                .build());

        try {
            requests.forEach(
                account -> {
                    RegisterResponse response = authService.register(account);
                    log.info("[LOG] - {} account with username {}", account.role(), account.username());
                }
            );
        } catch (DuplicatedException e) {
            throw new RuntimeException(e);
        }

    }
}
