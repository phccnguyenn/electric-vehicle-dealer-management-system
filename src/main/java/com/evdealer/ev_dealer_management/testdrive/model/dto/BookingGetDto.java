package com.evdealer.ev_dealer_management.testdrive.model.dto;

import com.evdealer.ev_dealer_management.testdrive.model.Booking;

public record BookingGetDto(
        Long id,
        SlotDetailsGetDto slot,
        String customerName,
        String customerPhone
) {
    public static BookingGetDto fromModel(Booking booking) {
        return new BookingGetDto(
                booking.getId(),
                SlotDetailsGetDto.fromModel(booking.getSlot()),
                booking.getCustomerName(),
                booking.getCustomerPhone()
        );
    }
}
