package com.evdealer.ev_dealer_management.car.model.dto.battery;


import java.util.List;

public record BatteryListGetDto(
        List<BatteryDetailGetDto> batteryInfoGetDtos,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
