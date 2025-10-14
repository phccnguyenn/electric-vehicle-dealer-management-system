package com.evdealer.ev_dealer_management.car.model.dto.dimension;

import com.evdealer.ev_dealer_management.car.model.Dimension;

public record DimensionDetailGetDto(

        Long id,
        int seatNumber,
        Float weightLbs,
        Float groundClearanceIn,
        Float widthFoldedIn,
        Float widthExtendedIn,
        Float heightIn,
        Float lengthMm,
        Float lengthIn,
        Float wheelsSizeCm

) {
    public static DimensionDetailGetDto fromModel(Dimension dimension) {
        return new DimensionDetailGetDto(
                dimension.getId(),
                dimension.getSeatNumber(),
                dimension.getWeightLbs(),
                dimension.getGroundClearanceIn(),
                dimension.getWidthFoldedIn(),
                dimension.getWidthExtendedIn(),
                dimension.getHeightIn(),
                dimension.getLengthMm(),
                dimension.getLengthIn(),
                dimension.getWheelsSizeCm()
        );
    }

}
