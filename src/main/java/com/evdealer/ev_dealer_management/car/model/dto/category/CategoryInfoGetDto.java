package com.evdealer.ev_dealer_management.car.model.dto.category;

import com.evdealer.ev_dealer_management.car.model.Category;
import com.evdealer.ev_dealer_management.car.model.dto.car.CarInfoGetDto;

import java.util.List;
import java.util.stream.Collectors;

public record CategoryInfoGetDto(
        Long id,
        String categoryName,
        List<CarInfoGetDto> cars
) {
    public static CategoryInfoGetDto fromModel(Category category) {
        return new CategoryInfoGetDto(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getCars().stream().map(car -> CarInfoGetDto.fromModel(car)).collect(Collectors.toList())
        );
    }
}
