package com.evdealer.ev_dealer_management.sale.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record PriceProgramPostDto(

        @NotNull(message = "Dealer hierarchy is required")
        @Max(value = 3, message = "Dealer hierarchy must be at most 3")
        @Min(value = 1, message = "Dealer hierarchy must be at least 1")
        Integer dealerHierarchy,

        @NotNull(message = "Start day is required")
        @Future(message = "Start day must be in the future")
        OffsetDateTime startDay,

        @NotNull(message = "End day is required")
        @Future(message = "End day must be in the future")
        OffsetDateTime endDay
) {
}
