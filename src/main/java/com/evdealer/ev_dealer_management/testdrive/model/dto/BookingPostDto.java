package com.evdealer.ev_dealer_management.testdrive.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookingPostDto(

        @NotNull(message = "The slot id must not be null")
        Long slotId,

        @NotBlank(message = "The customer name must not be blank")
        String customerName,

        @NotBlank(message = "The customer phone must not be blank")
        String customerPhone
) {
}
