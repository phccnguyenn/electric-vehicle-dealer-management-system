package com.evdealer.ev_dealer_management.car.model.dto.performance;

import com.evdealer.ev_dealer_management.car.model.enumeration.BatteryType;
import com.evdealer.ev_dealer_management.car.model.enumeration.MotorType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record PerformancePostDto(

        @Schema(allowableValues = {"STANDARD", "LONG_RANGE", "FAST_CHARGE"})
        BatteryType batteryType,

        @Schema(allowableValues = {"DC_BRUSHED", "DC_BRUSHLESS", "AC_INDUCTION", "PERMANENT_MAGNET", "SYNCHRONOUS"})
        MotorType motorType,

        @NotNull(message = "Range (miles) cannot be null")
        @DecimalMin(value = "0.0", inclusive = false, message = "Range (miles) must be greater than 0")
        @DecimalMax(value = "1000.0", message = "Range (miles) is too high")
        Float rangeMiles,

        @DecimalMin(value = "0.0", inclusive = false, message = "Acceleration (sec) must be greater than 0")
        @DecimalMax(value = "20.0", message = "Acceleration (sec) is too high")
        Float accelerationSec,

        @DecimalMin(value = "0.0", inclusive = false, message = "Top speed must be greater than 0")
        @DecimalMax(value = "400.0", message = "Top speed is unrealistic")
        Float topSpeedMph,

        @DecimalMin(value = "0.0", inclusive = false, message = "Towing capacity must be greater than 0")
        @DecimalMax(value = "10000.0", message = "Towing capacity is too high")
        Float towingLbs
) {
}

