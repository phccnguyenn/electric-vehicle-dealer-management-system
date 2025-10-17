package com.evdealer.ev_dealer_management.car.model.dto.battery;

import com.evdealer.ev_dealer_management.car.model.enumeration.ChemistryType;

public record PartialBatteryUpdateDto(
        ChemistryType chemistryType,
        Integer age,
        Integer chargeTime,
        Integer usageDuration,
        Float weightKg,
        Float voltageV,
        Float capacityKwh,
        Integer cycleLife
) {
}
