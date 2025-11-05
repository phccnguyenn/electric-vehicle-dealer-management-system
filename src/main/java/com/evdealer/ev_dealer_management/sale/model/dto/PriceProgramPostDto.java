package com.evdealer.ev_dealer_management.sale.model.dto;

import java.time.OffsetDateTime;

public record PriceProgramPostDto(
        Integer dealerHierarchy,
        OffsetDateTime startDay,
        OffsetDateTime endDay
) {
}
