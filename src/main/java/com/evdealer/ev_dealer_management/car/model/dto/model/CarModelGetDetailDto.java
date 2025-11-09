package com.evdealer.ev_dealer_management.car.model.dto.model;

import com.evdealer.ev_dealer_management.car.model.CarModel;
import com.evdealer.ev_dealer_management.car.model.dto.details.CarInfoGetDto;

import java.util.List;
import java.util.stream.Collectors;

public record CarModelGetDetailDto(
        Long carModelId,
        String categoryName,
        List<CarInfoGetDto> carInfoGetDtos
) {
    public static CarModelGetDetailDto fromModel(CarModel carModel) {
        return new CarModelGetDetailDto(
                carModel.getId(),
                carModel.getCarModelName(),
                carModel.getCarDetails().stream()
                        .map(CarInfoGetDto::fromModel)
                        .collect(Collectors.toList())
        );
    }
}
