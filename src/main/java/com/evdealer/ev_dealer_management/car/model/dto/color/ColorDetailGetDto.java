package com.evdealer.ev_dealer_management.car.model.dto.color;

import com.evdealer.ev_dealer_management.car.model.Color;

public record ColorDetailGetDto(
        Long id,
        String colorName,
        String colorHexCode
) {
    public static ColorDetailGetDto fromModel(Color color) {
        return new ColorDetailGetDto(
                color.getId(),
                color.getColorName(),
                color.getColorHexCode()
        );
    }
}
