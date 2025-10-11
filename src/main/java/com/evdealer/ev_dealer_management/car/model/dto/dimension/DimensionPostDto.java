package com.evdealer.ev_dealer_management.car.model.dto.dimension;

public record DimensionPostDto(
    Float lengthMm,
    Float weightLbs,
    Float groundClearanceIn,
    Float widthFoldedIn,
    Float widthExtendedIn,
    Float heightIn,
    Float lengthIn,
    Float wheelsSizeCm
) {
}
