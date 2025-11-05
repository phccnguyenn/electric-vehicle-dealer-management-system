package com.evdealer.ev_dealer_management.sale.model.dto;

import com.evdealer.ev_dealer_management.sale.model.PriceProgram;

import java.time.OffsetDateTime;
import java.util.List;

public record PriceProgramGetDto(
        Long priceProgramId,
        Integer dealerHierarchy,
        OffsetDateTime startDate,
        OffsetDateTime endDate,
        List<ProgramDetailGetDto> programDetails
) {
    public static PriceProgramGetDto fromModel(PriceProgram model) {
        if (model == null) {
            return null;
        }

        return new PriceProgramGetDto(
                model.getId(),
                model.getDealerHierarchy().getLevelType(),
                model.getStartDay() != null ? model.getStartDay() : null,
                model.getEndDay() != null ? model.getEndDay() : null,
                model.getProgramDetails().stream()
                        .map(ProgramDetailGetDto::fromModel)
                        .toList()
        );
    }
}
