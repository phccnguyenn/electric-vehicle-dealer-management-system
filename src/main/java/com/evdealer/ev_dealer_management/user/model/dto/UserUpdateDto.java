package com.evdealer.ev_dealer_management.user.model.dto;

public record UserUpdateDto(
        String fullName,
        String email,
        String phone
) {
}
