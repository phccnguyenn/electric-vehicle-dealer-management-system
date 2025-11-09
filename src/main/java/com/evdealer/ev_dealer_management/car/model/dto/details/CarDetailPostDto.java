package com.evdealer.ev_dealer_management.car.model.dto.details;

import com.evdealer.ev_dealer_management.car.model.dto.dimension.DimensionPostDto;
import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformancePostDto;
import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CarDetailPostDto(

        Long carModelId,

        @NotBlank(message = "Car name cannot be blank")
        @Size(max = 1000, message = "Car name must not exceed 1000 characters")
        String carName,

        CarStatus carStatus,

        String color,

        @Valid
        @NotNull(message = "Dimension information is required")
        DimensionPostDto dimensionPostDto,

        @Valid
        @NotNull(message = "Performance information is required")
        PerformancePostDto performancePostDto






) {
}
