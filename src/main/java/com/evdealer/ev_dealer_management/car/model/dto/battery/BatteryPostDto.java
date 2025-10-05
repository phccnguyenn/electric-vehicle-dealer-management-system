package com.evdealer.ev_dealer_management.car.model.dto.battery;

import com.evdealer.ev_dealer_management.car.model.enumeration.Chemistry;

import java.time.Duration;

public record BatteryPostDto(
        Chemistry chemistry,
        int age ,
        Duration chargeTime,
        Duration usageDuration,
        float weightKg,
        float voltageV,
        int capacityKWh,
        int cycleLife
) {
}
