package com.evdealer.ev_dealer_management.car.model.dto.motor;

import com.evdealer.ev_dealer_management.car.model.enumeration.CoolingType;
import com.evdealer.ev_dealer_management.car.model.enumeration.MotorType;

public record MotorPostDto(

    MotorType motorType,
    String serialNumber,
    Float powerKw,
    Float torqueNm,
    Integer maxRpm,
    CoolingType coolingType ,
    Float voltageRangeV

) {
}
