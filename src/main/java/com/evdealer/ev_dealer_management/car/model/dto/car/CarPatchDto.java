package com.evdealer.ev_dealer_management.car.model.dto.car;

import com.evdealer.ev_dealer_management.car.model.enumeration.DriveType;

import java.math.BigDecimal;

public record CarPatchDto(
        String categoryName,
        String carName,
        BigDecimal price,
        DriveType driveType,
        int seatNumber,
        int year
) {
}
