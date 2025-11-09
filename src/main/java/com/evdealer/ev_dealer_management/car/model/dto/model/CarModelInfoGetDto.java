package com.evdealer.ev_dealer_management.car.model.dto.model;

import com.evdealer.ev_dealer_management.car.model.CarModel;

public record CarModelInfoGetDto(
        Long id,
        String carModelName
) {
    public static CarModelInfoGetDto fromModel(CarModel carModel) {
        return new CarModelInfoGetDto(
                carModel.getId(),
                carModel.getCarModelName()
        );
    }
}
