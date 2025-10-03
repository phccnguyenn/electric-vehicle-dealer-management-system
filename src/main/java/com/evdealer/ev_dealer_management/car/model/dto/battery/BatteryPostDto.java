package com.evdealer.ev_dealer_management.car.model.dto.battery;

import java.time.Duration;

public record BatteryPostDto(
        String batteryType,
        Duration chargeTime,
        Duration usageDuration,
        float weight
) {
}
