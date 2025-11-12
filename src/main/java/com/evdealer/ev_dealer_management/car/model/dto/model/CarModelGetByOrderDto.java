package com.evdealer.ev_dealer_management.car.model.dto.model;

import com.evdealer.ev_dealer_management.car.model.CarModel;

public record CarModelGetByOrderDto (
        Long carModelId,
        String carModelName
) {
    public static CarModelGetByOrderDto fromModel(CarModel carModel) {
        return new CarModelGetByOrderDto(
                carModel.getId(),
                carModel.getCarModelName()
        );
    }
}
