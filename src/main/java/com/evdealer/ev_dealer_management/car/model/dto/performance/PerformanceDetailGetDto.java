package com.evdealer.ev_dealer_management.car.model.dto.performance;

import com.evdealer.ev_dealer_management.car.model.Performance;

public record PerformanceDetailGetDto(

        Long batteryId,
        Long motorId,
        Double rangeMiles,
        Double accelerationSec,
        Double topSpeedMph,
        Double towingLbs

) {
    public static PerformanceDetailGetDto fromModel(Performance performance) {
        return new PerformanceDetailGetDto(
                performance.getBattery().getBatteryId(),
                performance.getMotor().getMotorId(),
                performance.getRangeMiles(),
                performance.getAccelerationSec(),
                performance.getTopSpeedMph(),
                performance.getTowingLbs()
        );
    }
}
