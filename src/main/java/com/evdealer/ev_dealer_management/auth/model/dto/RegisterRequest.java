package com.evdealer.ev_dealer_management.auth.model.dto;

import com.evdealer.ev_dealer_management.user.model.DealerInfo;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterRequest(
        @NotBlank(message = "Username must not be blank") String username,
        @NotBlank(message = "Password must not be blank") String password,
        @NotBlank(message = "Full name must not be blank") String fullName,
        String email,
        @NotBlank(message = "Phone must not be blank") String phone,
        String city,
        Boolean isActive,
        RoleType role,
        DealerInfo dealerInfo
) {
}
