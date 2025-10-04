package com.evdealer.ev_dealer_management.car.model.dto.car;

import com.evdealer.ev_dealer_management.car.model.dto.color.ColorPostDto;
import com.evdealer.ev_dealer_management.car.model.enumeration.DriveType;

public record CarPostDto(
        Long categoryId,
        String carName,
        DriveType driveType,
        int seatNumber,
        int year,
        ColorPostDto colorPostDto
) {
}
