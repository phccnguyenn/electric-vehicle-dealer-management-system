package com.evdealer.ev_dealer_management.car.model.dto.battery;

import com.evdealer.ev_dealer_management.car.model.enumeration.ChemistryType;

public record BatteryPostDto(
        ChemistryType chemistryType,
        int capacityKWh,
        int cycleLife
) {
}
