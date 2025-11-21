package com.evdealer.ev_dealer_management.car.model.dto.details;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.car.model.dto.image.CarImageGetDetailDto;

import java.util.List;
import java.util.stream.Collectors;

public record CarInfoGetDto(
        Long carId,
        String carName,
        String vinNumber,
        String engineNumber,
        List<CarImageGetDetailDto> carImages
) {
    public static CarInfoGetDto fromModel(CarDetail carDetail) {
        return new CarInfoGetDto(
                carDetail.getId(),
                carDetail.getCarName(),
                carDetail.getVinNumber(),
                carDetail.getEngineNumber(),
                carDetail.getCarImages().stream()
                        .map(CarImageGetDetailDto::fromModel)
                        .collect(Collectors.toList())
        );
    }
}
