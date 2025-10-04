package com.evdealer.ev_dealer_management.car.model.dto.color;


public record ColorPostDto (
    String colorName,
    String colorHex,
    Float extraCost
) {
}
