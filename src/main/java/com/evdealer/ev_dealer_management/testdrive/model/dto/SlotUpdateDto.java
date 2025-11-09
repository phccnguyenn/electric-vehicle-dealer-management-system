package com.evdealer.ev_dealer_management.testdrive.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record SlotUpdateDto(

        @NotNull(message = "The number of customers must not be null")
        @Min(value = 1, message = "The number of customers must be greater than 1")
        Integer newNumCustomers,

        @Future(message = "New start time must be in the future")
        OffsetDateTime newStartTime,

        @Future(message = "New end time must be in the future")
        OffsetDateTime newEndTime

) {
}
