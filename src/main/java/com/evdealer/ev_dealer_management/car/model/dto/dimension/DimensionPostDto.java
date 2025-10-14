package com.evdealer.ev_dealer_management.car.model.dto.dimension;

public record DimensionPostDto(
    Integer seatNumber,
    Float weightLbs,
    Float groundClearanceIn,
    Float widthFoldedIn,
    Float widthExtendedIn,
    Float lengthMm,
    Float heightIn,
    Float lengthIn,
    Float wheelsSizeCm
) {
}
