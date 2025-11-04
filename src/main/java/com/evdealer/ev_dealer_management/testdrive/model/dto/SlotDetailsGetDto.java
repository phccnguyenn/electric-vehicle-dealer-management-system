package com.evdealer.ev_dealer_management.testdrive.model.dto;

import com.evdealer.ev_dealer_management.testdrive.model.Booking;
import com.evdealer.ev_dealer_management.testdrive.model.Slot;

import java.time.OffsetDateTime;
import java.util.List;

public record SlotDetailsGetDto (
        Long id,
        Long deaderId,
        String carName,
        Integer amount,
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        Integer bookedCount
) {
    public static SlotDetailsGetDto fromModel(Slot slot) {
        return new SlotDetailsGetDto(
                slot.getId(),
                slot.getDealer().getId(),
                slot.getCar().getCarName(),
                slot.getAmount(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.getBookings().size()
        );
    }
}
