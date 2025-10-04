package com.evdealer.ev_dealer_management.car.model.dto.performance;

import com.evdealer.ev_dealer_management.car.model.enumeration.Chemistry;

public record PerformancePostDto(
        String motorType,
        int horsepower,
        int torque,
        int topSpeed,
        Chemistry chemistry
         {
}
