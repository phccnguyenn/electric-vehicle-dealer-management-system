package com.evdealer.ev_dealer_management.car.model.dto.performance;

import com.evdealer.ev_dealer_management.car.model.enumeration.ChemistryType;

public record PerformancePostDto(
        Long batteryId,
        Long motorId,
        Float rangeMiles,
        Float accelerationSec,
        Float topSpeedMph,
        Float towingLbs
) {
}

