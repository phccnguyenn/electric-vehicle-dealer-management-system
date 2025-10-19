package com.evdealer.ev_dealer_management.user.model.dto.account;

public record UserUpdateDto(
        String fullName,
        String email,
        String phone
) {
}
