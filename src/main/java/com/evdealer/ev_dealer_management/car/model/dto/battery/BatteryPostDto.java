package com.evdealer.ev_dealer_management.car.model.dto.battery;

import com.evdealer.ev_dealer_management.car.model.enumeration.ChemistryType;

import java.time.Duration;

public record BatteryPostDto(
        ChemistryType chemistryType,
        Integer age ,
        Integer chargeTime,
        Integer usageDuration,
        Float weightKg,
        Float voltageV,
        Float capacityKWh,
        Integer cycleLife
) {
}
