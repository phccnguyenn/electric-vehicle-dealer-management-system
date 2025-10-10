package com.evdealer.ev_dealer_management.car.model.dto.car;

import com.evdealer.ev_dealer_management.car.model.dto.color.ColorPostDto;
import com.evdealer.ev_dealer_management.car.model.dto.dimension.DimensionPostDto;
import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformancePostDto;
import com.evdealer.ev_dealer_management.car.model.enumeration.DriveType;

import java.math.BigDecimal;

public record CarPostDto(
        Long categoryId,
        String carName,
        BigDecimal price,
        DriveType driveType,
        int seatNumber,
        int year,
        ColorPostDto colorPostDto,
        DimensionPostDto dimensionPostDto,
        PerformancePostDto performancePostDto
) {
}
