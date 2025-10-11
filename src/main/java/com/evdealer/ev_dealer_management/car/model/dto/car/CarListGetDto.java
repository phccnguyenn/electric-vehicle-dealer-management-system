package com.evdealer.ev_dealer_management.car.model.dto.car;

import java.util.List;

public record CarListGetDto(
        List<CarInfoGetDto> carInfoGetDtos,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
