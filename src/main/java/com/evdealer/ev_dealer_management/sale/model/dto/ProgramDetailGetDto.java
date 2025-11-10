package com.evdealer.ev_dealer_management.sale.model.dto;

import com.evdealer.ev_dealer_management.sale.model.ProgramDetail;

import java.math.BigDecimal;

public record ProgramDetailGetDto(
        Long id,
        Long priceProgramId,
        String carModelName,
        BigDecimal minPrice,
        BigDecimal suggestedPrice,
        BigDecimal maxPrice
) {
    public static ProgramDetailGetDto fromModel(ProgramDetail model) {
        return new ProgramDetailGetDto (
                model.getId(),
                model.getPriceProgram().getId(),
                model.getCarModel().getCarModelName(),
                model.getMinPrice(),
                model.getSuggestedPrice(),
                model.getMaxPrice()
        );
    }
}
