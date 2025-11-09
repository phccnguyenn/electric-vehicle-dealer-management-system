package com.evdealer.ev_dealer_management.car.model.dto.model;


import java.util.List;

public record CarModelListGetDto(
        List<CarModelInfoGetDto> carInfoGetDtos
) {
}
