package com.evdealer.ev_dealer_management.car.model.dto.battery;

import com.evdealer.ev_dealer_management.car.model.enumeration.Chemistry;

public record BatteryPostDto(
        Chemistry chemistry,
        int capacityKWh,
        int cycleLife
) {
}
