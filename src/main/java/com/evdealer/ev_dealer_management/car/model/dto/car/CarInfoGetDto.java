package com.evdealer.ev_dealer_management.car.model.dto.car;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.CarImage;

import java.math.BigDecimal;

public record CarInfoGetDto(
        Long carId,
        String carName,
        BigDecimal price,
        String firstCarImageUrl
) {
    public static CarInfoGetDto fromModel(Car car) {
        return new CarInfoGetDto(
                car.getId(),
                car.getCarName(),
                car.getPrice(),
                car.getCarImages().get(0).getFileUrl()
        );
    }
}
