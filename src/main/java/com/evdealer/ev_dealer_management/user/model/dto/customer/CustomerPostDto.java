package com.evdealer.ev_dealer_management.user.model.dto.customer;

public record CustomerPostDto(
        String fullName,
        String email,
        String phone,
        String address
) {
}
