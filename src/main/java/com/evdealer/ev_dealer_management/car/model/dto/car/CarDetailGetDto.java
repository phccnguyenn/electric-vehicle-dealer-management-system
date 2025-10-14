package com.evdealer.ev_dealer_management.car.model.dto.car;

import com.evdealer.ev_dealer_management.car.model.*;
import com.evdealer.ev_dealer_management.car.model.dto.category.CategoryGetDetailDto;
import com.evdealer.ev_dealer_management.car.model.dto.category.CategoryInfoGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.color.ColorDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.dimension.DimensionDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.image.CarImageGetDetailDto;
import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformanceDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.enumeration.DriveType;

import java.util.List;
import java.util.stream.Collectors;

public record CarDetailGetDto (
        Long id,
        String carName,
        DriveType driveType,
        int year,
        CategoryInfoGetDto category,
        DimensionDetailGetDto dimension,
        ColorDetailGetDto color,
        PerformanceDetailGetDto performanceDetailGetDto,
        List<CarImageGetDetailDto> carImages
) {
    public static CarDetailGetDto fromModel(Car car) {
        return new CarDetailGetDto (
                car.getId(),
                car.getCarName(),
                car.getDriveType(),
                car.getYear(),
                CategoryInfoGetDto.fromModel(car.getCategory()),
                DimensionDetailGetDto.fromModel(car.getDimension()),
                ColorDetailGetDto.fromModel(car.getColor()),
                PerformanceDetailGetDto.fromModel(car.getPerformance()),
                car.getCarImages().stream()
                        .map(CarImageGetDetailDto::fromModel)
                        .collect(Collectors.toList())
        );
    }
}
