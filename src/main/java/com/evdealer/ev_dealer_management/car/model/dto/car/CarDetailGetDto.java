package com.evdealer.ev_dealer_management.car.model.dto.car;

import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;

public record CarDetailGetDto (
        Long id,
        String carName,
        String carModel,
        String description,
        CarStatus status
) {
}
