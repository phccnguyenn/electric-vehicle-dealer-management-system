package com.evdealer.ev_dealer_management.car.model.dto.performance;

import com.evdealer.ev_dealer_management.car.model.Performance;
import com.evdealer.ev_dealer_management.car.model.enumeration.BatteryType;
import com.evdealer.ev_dealer_management.car.model.enumeration.MotorType;

public record PerformanceDetailGetDto(

        Float rangeMiles,
        Float accelerationSec,
        Float topSpeedMph,
        Float towingLbs,
        BatteryType battery,
        MotorType motor

) {
    public static PerformanceDetailGetDto fromModel(Performance performance) {
        return new PerformanceDetailGetDto(
                performance.getRangeMiles(),
                performance.getAccelerationSec(),
                performance.getTopSpeedMph(),
                performance.getTowingLbs(),
                performance.getBatteryType(),
                performance.getMotorType()
        );
    }
}
