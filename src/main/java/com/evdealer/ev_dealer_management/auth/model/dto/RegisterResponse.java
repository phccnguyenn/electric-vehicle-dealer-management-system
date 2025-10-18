package com.evdealer.ev_dealer_management.auth.model.dto;

import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;

public record RegisterResponse (
    String username,
    String fullName,
    String email,
    String phone,
    RoleType role
) {
    public static RegisterResponse fromModel(User user) {
        return new RegisterResponse(
            user.getUsername(),
            user.getFullName(),
            user.getEmail(),
            user.getPhone(),
            user.getRole()
        );
    }
}
