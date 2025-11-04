package com.evdealer.ev_dealer_management.testdrive.model.dto;

import java.time.OffsetDateTime;

public record SlotPostDto (
        Long carId,
        Integer amount,
        OffsetDateTime startTime,
        OffsetDateTime endTime
) {
}
