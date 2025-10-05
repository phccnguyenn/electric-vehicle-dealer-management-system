package com.evdealer.ev_dealer_management.car.model.dto.performance;

import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryPostDto;
import com.evdealer.ev_dealer_management.car.model.enumeration.Chemistry;

public record PerformancePostDto(

        Long carId,
        Long batteryId,
        Long motorId,
        String motorType,
        Double rangeMiles,
        Double accelerationSec,
        Double topSpeedMph,
        Chemistry chemistry )
         {
}

