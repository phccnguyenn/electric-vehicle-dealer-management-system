package com.evdealer.ev_dealer_management.car.model.dto.details;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.car.model.dto.dimension.DimensionDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.image.CarImageGetDetailDto;
import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformanceDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;

import java.util.List;
import java.util.stream.Collectors;

public record CarDetailForModelGetDto (
        Long carDetailId,
        String carName,
        CarStatus carStatus,
        String vinNumber,
        String engineNumber,
        DimensionDetailGetDto dimension,
        PerformanceDetailGetDto performanceDetailGetDto,
        List<CarImageGetDetailDto> carImages
) {
    public static CarDetailForModelGetDto fromModel(CarDetail carDetail) {
        return new CarDetailForModelGetDto(
                carDetail.getId(),
                carDetail.getCarName(),
                carDetail.getCarStatus(),
                carDetail.getVinNumber(),
                carDetail.getEngineNumber(),
                DimensionDetailGetDto.fromModel(carDetail.getDimension()),
                PerformanceDetailGetDto.fromModel(carDetail.getPerformance()),
                carDetail.getCarImages().stream()
                        .map(CarImageGetDetailDto::fromModel)
                        .collect(Collectors.toList())
        );
    }
}
