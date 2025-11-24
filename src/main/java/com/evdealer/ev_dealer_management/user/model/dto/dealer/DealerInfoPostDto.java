package com.evdealer.ev_dealer_management.user.model.dto.dealer;

public record DealerInfoPostDto (
        String dealerName,
        String dealerPhone,
        Long dealerLevel,
        String location
) {
}
