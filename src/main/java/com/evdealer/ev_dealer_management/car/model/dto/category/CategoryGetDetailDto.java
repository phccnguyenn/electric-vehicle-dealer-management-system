package com.evdealer.ev_dealer_management.car.model.dto.category;

import com.evdealer.ev_dealer_management.car.model.Category;
import com.evdealer.ev_dealer_management.car.model.dto.car.CarInfoGetDto;
import com.evdealer.ev_dealer_management.car.model.enumeration.CategoryType;

import java.util.List;
import java.util.stream.Collectors;

public record CategoryGetDetailDto (
        Long id,
        String categoryName,
        List<CarInfoGetDto> carInfoGetDtos
) {
    public static CategoryGetDetailDto fromModel(Category category) {
        return new CategoryGetDetailDto(
                category.getId(),
                category.getCategoryName(),
                category.getCars().stream().map(CarInfoGetDto::fromModel).collect(Collectors.toList())
        );
    }
}
