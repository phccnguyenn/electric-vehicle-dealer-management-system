package com.evdealer.ev_dealer_management.car.model.dto.car;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.dto.image.CarImageGetDetailDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record CarInfoGetDto(
        Long carId,
        String carName,
        BigDecimal price,
        List<CarImageGetDetailDto> carImages
) {
    public static CarInfoGetDto fromModel(Car car) {
        return new CarInfoGetDto(
                car.getId(),
                car.getCarName(),
                car.getPrice(),
                car.getCarImages().stream()
                        .map(CarImageGetDetailDto::fromModel)
                        .collect(Collectors.toList())
        );
    }
}
