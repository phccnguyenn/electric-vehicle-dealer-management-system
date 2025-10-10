package com.evdealer.ev_dealer_management.car.model.dto.car;

import com.evdealer.ev_dealer_management.car.model.*;
import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformanceDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.enumeration.DriveType;

public record CarDetailGetDto (
        Long id,
        String carName,
        DriveType driveType,
        int seatNumber,
        int year,
        Color color,
        Category category,
        Interior interior,
        Dimension dimension,
        PerformanceDetailGetDto performanceDetailGetDto
) {
    public static CarDetailGetDto fromModel(Car car) {
        return new CarDetailGetDto (
                car.getId(),
                car.getCarName(),
                car.getDriveType(),
                car.getSeatNumber(),
                car.getYear(),
                null,
                null,
                null,
                null,
                PerformanceDetailGetDto.fromModel(car.getPerformance())
        );
    }
}
