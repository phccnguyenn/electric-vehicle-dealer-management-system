package com.evdealer.ev_dealer_management.testdrive.model.dto;

import com.evdealer.ev_dealer_management.testdrive.model.Booking;

public record BookingGetDto(
        Long id,
        Long slotId,
        String customerPhone
) {
    public static BookingGetDto fromModel(Booking booking) {
        return new BookingGetDto(
                booking.getId(),
                booking.getSlot().getId(),
                booking.getCustomerPhone()
        );
    }
}
