package com.evdealer.ev_dealer_management.car.model.dto.category;

import com.evdealer.ev_dealer_management.car.model.Category;
import com.evdealer.ev_dealer_management.car.model.enumeration.CategoryType;

public record CategoryGetDetailDto (
        Long id,
        CategoryType categoryType
) {
    public static CategoryGetDetailDto fromModel(Category category) {
        return new CategoryGetDetailDto(
                category.getCategoryId(),
                category.getCategoryType()
        );
    }
}
