package com.evdealer.ev_dealer_management.testdrive.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CarModelInSlotPostDto(

        @NotNull(message = "The carDetail model id must not be null")
        Long carModelId,

        @NotNull(message = "The number of trial cars must not be null")
        @Min(value = 1, message = "The number of trials carDetail must be greater than 1")
        Integer maxTrialCar

) {
}
