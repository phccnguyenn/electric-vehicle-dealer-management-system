package com.evdealer.ev_dealer_management.car.model.dto.model;

import com.evdealer.ev_dealer_management.car.model.CarModel;
import com.evdealer.ev_dealer_management.car.model.dto.details.CarDetailForModelGetDto;

import java.util.List;

public record CarModelInfoGetDto(
        Long id,
        String carModelName,
        List<CarDetailForModelGetDto> carDetails
) {
    public static CarModelInfoGetDto fromModel(CarModel carModel) {
        return new CarModelInfoGetDto(
                carModel.getId(),
                carModel.getCarModelName(),
                carModel.getCarDetails().stream()
                        .map(CarDetailForModelGetDto::fromModel)
                        .toList()
        );
    }
}
