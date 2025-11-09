package com.evdealer.ev_dealer_management.car.model.dto.details;

import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;

public record CarPatchDto(
        Long carModelId,
        String carName,
        CarStatus carStatus,
        String color
) {
}
