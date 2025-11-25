package com.evdealer.ev_dealer_management.sale.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record PriceProgramPostDto(

        @NotBlank(message = "Price program name must not be blank")
        String programName,

        Boolean isActive,

        @NotNull(message = "Effective date is required")
        @Future(message = "Effective date must be in the future")
        OffsetDateTime startDay

) {
}
