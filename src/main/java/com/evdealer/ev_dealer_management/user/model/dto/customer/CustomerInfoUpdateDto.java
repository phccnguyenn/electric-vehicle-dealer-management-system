package com.evdealer.ev_dealer_management.user.model.dto.customer;

public record CustomerInfoUpdateDto (
        String fullName,
        String email,
        String phone,
        String address
) {
}
