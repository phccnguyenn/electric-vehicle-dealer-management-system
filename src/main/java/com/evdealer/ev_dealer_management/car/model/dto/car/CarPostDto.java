package com.evdealer.ev_dealer_management.car.model.dto.car;

import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;
import com.evdealer.ev_dealer_management.car.model.enumeration.DriveType;

public record CarPostDto(
        String carName,
        DriveType driveType,
        int seatNumber,
        int year

) {
}
