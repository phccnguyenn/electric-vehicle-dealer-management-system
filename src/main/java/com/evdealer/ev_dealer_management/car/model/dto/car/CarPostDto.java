package com.evdealer.ev_dealer_management.car.model.dto.car;

import com.evdealer.ev_dealer_management.car.model.dto.color.ColorPostDto;
import com.evdealer.ev_dealer_management.car.model.dto.dimension.DimensionPostDto;
import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformancePostDto;
import com.evdealer.ev_dealer_management.car.model.enumeration.DriveType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record CarPostDto(
        Long categoryId,
        ColorPostDto colorPostDto,
        String carName,
        BigDecimal price,
        DriveType driveType,
        int year,
        DimensionPostDto dimensionPostDto,
        PerformancePostDto performancePostDto
) {
}
