package com.evdealer.ev_dealer_management.car.model.dto.battery;

import com.evdealer.ev_dealer_management.car.model.enumeration.ChemistryType;

import java.time.Duration;

public record BatteryPostDto(
        ChemistryType chemistryType,
        int age ,
        Integer chargeTime,
        Integer usageDuration,
        float weightKg,
        float voltageV,
        int capacityKWh,
        int cycleLife
) {
}
