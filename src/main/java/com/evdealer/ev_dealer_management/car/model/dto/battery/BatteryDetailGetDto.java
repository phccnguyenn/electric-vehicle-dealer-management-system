package com.evdealer.ev_dealer_management.car.model.dto.battery;

import com.evdealer.ev_dealer_management.car.model.Battery;
import com.evdealer.ev_dealer_management.car.model.enumeration.ChemistryType;

import java.time.Duration;

public record BatteryDetailGetDto(
        Long id,
        ChemistryType chemistryType,
        Integer age,
        Integer chargeTime,
        Integer usageDuration,
        Float weightKg,
        Float voltageV,
        Float capacityKwh,
        Integer cycleLife
) {

    public static BatteryDetailGetDto fromModel(Battery battery) {
        return new BatteryDetailGetDto(
                battery.getBatteryId(),
                battery.getChemistryType(),
                battery.getAge(),
                battery.getChargeTime(),
                battery.getUsageDuration(),
                battery.getWeightKg(),
                battery.getVoltageV(),
                battery.getCapacityKwh(),
                battery.getCycleLife()
        );
    }

}
