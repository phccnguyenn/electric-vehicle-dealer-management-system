package com.evdealer.ev_dealer_management.auth.model.dto;

import com.evdealer.ev_dealer_management.auth.model.enumeration.RoleType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterRequest(
        @NotBlank(message = "Username must not be blank") String username,
        @NotBlank(message = "Password must not be blank") String password,
        @NotBlank(message = "Full name must not be blank") String fullName,
        String email,
        String phone,
        Boolean isActive,
        RoleType role
) {
}
