package com.evdealer.ev_dealer_management.testdrive.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record SlotPostDto (

        @NotNull(message = "The dealer staff id must not be null")
        Long dealerStaffId,

        CarModelInSlotPostDto carModelInSlotPostDto,

        @NotNull(message = "The number of customers must not be null")
        @Min(value = 1, message = "The number of customers must be greater than 1")
        Integer numCustomers,

        @Future(message = "Start time must be in the future")
        OffsetDateTime startTime,

        @Future(message = "End time must be in the future")
        OffsetDateTime endTime
) {
}
