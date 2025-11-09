package com.evdealer.ev_dealer_management.car.model.dto.dimension;

import jakarta.validation.constraints.*;

public record DimensionPostDto(

        @NotNull(message = "Seat number cannot be null")
        @Min(value = 1, message = "Seat number must be at least 1")
        @Max(value = 9, message = "Seat number cannot exceed 9")
        Integer seatNumber,

        @NotNull(message = "Weight cannot be null")
        @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be greater than 0")
        @DecimalMax(value = "10000.0", message = "Weight is too high")
        Float weightLbs,

        @DecimalMin(value = "0.0", inclusive = false, message = "Ground clearance must be greater than 0")
        @DecimalMax(value = "20.0", message = "Ground clearance is too high")
        Float groundClearanceIn,

        @DecimalMin(value = "0.0", inclusive = false, message = "Width (folded) must be greater than 0")
        @DecimalMax(value = "120.0", message = "Width (folded) is too high")
        Float widthFoldedIn,

        @DecimalMin(value = "0.0", inclusive = false, message = "Width (extended) must be greater than 0")
        @DecimalMax(value = "120.0", message = "Width (extended) is too high")
        Float widthExtendedIn,

        @DecimalMin(value = "0.0", inclusive = false, message = "Length (mm) must be greater than 0")
        @DecimalMax(value = "10000.0", message = "Length (mm) is too high")
        Float lengthMm,

        @DecimalMin(value = "0.0", inclusive = false, message = "Height must be greater than 0")
        @DecimalMax(value = "120.0", message = "Height is too high")
        Float heightIn,

        @DecimalMin(value = "0.0", inclusive = false, message = "Length (in) must be greater than 0")
        @DecimalMax(value = "400.0", message = "Length (in) is too high")
        Float lengthIn,

        @DecimalMin(value = "0.0", inclusive = false, message = "Wheel size must be greater than 0")
        @DecimalMax(value = "100.0", message = "Wheel size is too high")
        Float wheelsSizeCm

) {
}
