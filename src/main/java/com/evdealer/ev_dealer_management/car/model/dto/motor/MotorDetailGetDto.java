package com.evdealer.ev_dealer_management.car.model.dto.motor;

import com.evdealer.ev_dealer_management.car.model.Motor;
import com.evdealer.ev_dealer_management.car.model.enumeration.CoolingType;
import com.evdealer.ev_dealer_management.car.model.enumeration.MotorType;

public record MotorDetailGetDto(
        Long motorId,
        MotorType motorType,
        String serialNumber,
        Float powerKw,
        Float torqueNm,
        Integer maxRpm,
        CoolingType coolingType,
        Float voltageRangeV
) {

    public static MotorDetailGetDto fromModel(Motor motor) {
        return new MotorDetailGetDto(
                motor.getMotorId(),
                motor.getMotorType(),
                motor.getSerialNumber(),
                motor.getPowerKw(),
                motor.getTorqueNm(),
                motor.getMaxRpm(),
                motor.getCoolingType(),
                motor.getVoltageRangeV()
        );
    }
}