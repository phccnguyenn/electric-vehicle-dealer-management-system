package com.evdealer.ev_dealer_management.user.model.dto.account;

import com.evdealer.ev_dealer_management.user.model.User;

public record UserProfileGetDto(
        Long userId,
        String username,
        String fullName,
        String email,
        String phone,
        String role,
        boolean isActive
) {
    public static UserProfileGetDto fromModel(User user) {
        return new UserProfileGetDto(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole() != null ? user.getRole().name() : null,
                user.isActive()
        );
    }
}
