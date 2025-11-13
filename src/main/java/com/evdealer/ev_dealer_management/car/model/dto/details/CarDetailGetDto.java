package com.evdealer.ev_dealer_management.car.model.dto.details;

import com.evdealer.ev_dealer_management.car.model.*;
import com.evdealer.ev_dealer_management.car.model.dto.model.CarModelInfoGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.dimension.DimensionDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.image.CarImageGetDetailDto;
import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformanceDetailGetDto;

import java.util.List;
import java.util.stream.Collectors;

public record CarDetailGetDto (
        Long carDetailId,
        String carModelName,
        String carName,
        String color,
        DimensionDetailGetDto dimension,
        PerformanceDetailGetDto performanceDetailGetDto,
        List<CarImageGetDetailDto> carImages
) {
    public static CarDetailGetDto fromModel(CarDetail carDetail) {
        return new CarDetailGetDto (
                carDetail.getId(),
                CarModelInfoGetDto.fromModel(carDetail.getCarModel()).carModelName(),
                carDetail.getCarName(),
                carDetail.getColor(),
                DimensionDetailGetDto.fromModel(carDetail.getDimension()),
                PerformanceDetailGetDto.fromModel(carDetail.getPerformance()),
                carDetail.getCarImages().stream()
                        .map(CarImageGetDetailDto::fromModel)
                        .collect(Collectors.toList())
        );
    }
}
