package com.evdealer.ev_dealer_management.car.model.dto.category;


import java.util.List;

public record CategoryListGetDto(
        List<CategoryInfoGetDto> carInfoGetDtos,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
