package com.evdealer.ev_dealer_management.car.model.dto.performance;

import com.evdealer.ev_dealer_management.car.model.Performance;
import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.motor.MotorDetailGetDto;

public record PerformanceDetailGetDto(

        Float rangeMiles,
        Float accelerationSec,
        Float topSpeedMph,
        Float towingLbs,
        BatteryDetailGetDto battery,
        MotorDetailGetDto motor

) {
    public static PerformanceDetailGetDto fromModel(Performance performance) {
        return new PerformanceDetailGetDto(
                performance.getRangeMiles(),
                performance.getAccelerationSec(),
                performance.getTopSpeedMph(),
                performance.getTowingLbs(),
                BatteryDetailGetDto.fromModel(performance.getBattery()),
                MotorDetailGetDto.fromModel(performance.getMotor())
        );
    }
}
