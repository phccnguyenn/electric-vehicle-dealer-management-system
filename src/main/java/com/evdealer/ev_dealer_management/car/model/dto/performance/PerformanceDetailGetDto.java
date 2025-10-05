package com.evdealer.ev_dealer_management.car.model.dto.performance;

public record PerformanceDetailGetDto(
        Long id,
        String acceleration,
        String topSpeed,
        String horsepower,
        String torque
) {
}
