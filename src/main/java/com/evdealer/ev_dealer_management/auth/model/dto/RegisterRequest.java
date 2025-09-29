package com.evdealer.ev_dealer_management.auth.model.dto;

import com.evdealer.ev_dealer_management.auth.model.enumeration.RoleType;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String fullName,
        @NotBlank String email,
        @NotBlank String phone,
        Boolean isActive,
        RoleType role
) {
}
