package com.evdealer.ev_dealer_management.car.model.dto.details;

import io.swagger.v3.oas.annotations.media.Schema;

public record CarPatchDto(
        @Schema(allowableValues = {"TESLA_MODEL_3", "TESLA_MODEL_Y", "TESLA_MODEL_S", "TESLA_MODEL_X", "TESLA_CYBERTRUCK", "TESLA_MODEL_Z"})
        String carModel,
        String carName
) {
}
