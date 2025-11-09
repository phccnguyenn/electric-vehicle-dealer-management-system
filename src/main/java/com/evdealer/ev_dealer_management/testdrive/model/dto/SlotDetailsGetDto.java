package com.evdealer.ev_dealer_management.testdrive.model.dto;

import com.evdealer.ev_dealer_management.testdrive.model.Slot;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record SlotDetailsGetDto (
        Long id,
        String deaderStaffName,
        List<CarModelInSlotDetailDto> carModelInSlotDetailDto,
        Integer numCustomers,
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        Integer bookedCount
) {
    public static SlotDetailsGetDto fromModel(Slot slot) {

        Integer bookedCount = (slot.getBookings() == null) ? 0 : slot.getBookings().size();

        return new SlotDetailsGetDto(
                slot.getId(),
                slot.getDealerStaff().getFullName(),
                slot.getCarModelInSlots().stream()
                                .map(CarModelInSlotDetailDto::fromModel)
                                .collect(Collectors.toList()),
                slot.getNumCustomers(),
                slot.getStartTime(),
                slot.getEndTime(),
                bookedCount
        );
    }
}
