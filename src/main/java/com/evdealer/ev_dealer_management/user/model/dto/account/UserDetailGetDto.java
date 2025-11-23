package com.evdealer.ev_dealer_management.user.model.dto.account;

import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;

public record UserDetailGetDto(
        Long id,
        String username,
        String fullName,
        String email,
        String phone,
        boolean isActive,
        RoleType role,
        String city
) {
    public static UserDetailGetDto fromModel(User user) {
        return new UserDetailGetDto(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.isActive(),
                user.getRole(),
                user.getAddress()
        );
    }
}
