package com.evdealer.ev_dealer_management.car.model.dto.motor;

import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryDetailGetDto;

import java.util.List;

public record MotorListDto(
        List<MotorDetailGetDto> motorDetailGetDtos,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
