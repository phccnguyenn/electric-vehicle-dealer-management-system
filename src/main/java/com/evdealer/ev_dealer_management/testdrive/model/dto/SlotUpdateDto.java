package com.evdealer.ev_dealer_management.testdrive.model.dto;

import java.time.OffsetDateTime;

public record SlotUpdateDto(
        Long slotId,
        OffsetDateTime newStartTime,
        OffsetDateTime newEndTime,
        Integer newAmount
) {
}
