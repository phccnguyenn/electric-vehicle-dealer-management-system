package com.evdealer.ev_dealer_management.car.model.dto;

import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;

public record CarPostDto(
        String carName,
        String carModel,
        String description,
        CarStatus status
) {
}
