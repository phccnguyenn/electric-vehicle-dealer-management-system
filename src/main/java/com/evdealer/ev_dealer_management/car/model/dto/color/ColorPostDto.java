package com.evdealer.ev_dealer_management.car.model.dto.color;


import java.math.BigDecimal;

public record ColorPostDto (
    String colorName,
    String colorHex,
    BigDecimal extraCost
) {
}
