package com.evdealer.ev_dealer_management.user.model.dto.account;

import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;

public record UserPostDto(
        String username,
        String password,
        String fullName,
        String email,
        String phone,
        boolean isActive,
        RoleType role
) {
}
