package com.evdealer.ev_dealer_management.car.model.dto.car;

import com.evdealer.ev_dealer_management.car.model.enumeration.DriveType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record CarPatchDto(
        String categoryName,
        String carName,
        BigDecimal price,
        @Schema(allowableValues = {"FWD", "RWD", "AWD", "FOUR_WD"})
        DriveType driveType,
        int year
) {
}
