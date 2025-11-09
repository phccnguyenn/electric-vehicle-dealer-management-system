package com.evdealer.ev_dealer_management.car.model.dto.details;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;

public record CarDetailForModelGetDto (
        Long carDetailId,
        String carName,
        CarStatus carStatus
) {
    public static CarDetailForModelGetDto fromModel(CarDetail carDetail) {
        return new CarDetailForModelGetDto(carDetail.getId(), carDetail.getCarName(), carDetail.getCarStatus());
    }
}
