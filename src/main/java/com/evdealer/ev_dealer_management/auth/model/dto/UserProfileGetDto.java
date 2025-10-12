package com.evdealer.ev_dealer_management.auth.model.dto;

import com.evdealer.ev_dealer_management.auth.model.User;

public record UserProfileGetDto(
        String username,
        String fullName,
        String email,
        String phone,
        String role,
        boolean isActive
) {
    public static UserProfileGetDto fromModel(User user) {
        return new UserProfileGetDto(
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole() != null ? user.getRole().name() : null,
                user.isActive()
        );
    }
}
