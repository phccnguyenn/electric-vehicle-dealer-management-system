package com.evdealer.ev_dealer_management.sale.model.dto;

import com.evdealer.ev_dealer_management.sale.model.PriceProgram;

import java.time.OffsetDateTime;
import java.util.List;

public record PriceProgramGetDto(
        Long priceProgramId,
        String priceProgramName,
        OffsetDateTime effectiveDate,
        boolean isActive,
        List<ProgramDetailGetDto> programDetails,
        String createdBy,
        OffsetDateTime createdOn,
        String lastModifiedBy,
        OffsetDateTime lastModifiedOn
) {
    public static PriceProgramGetDto fromModel(PriceProgram model) {

        if (model == null) {
            return null;
        }

        List<ProgramDetailGetDto> details = model.getProgramDetails() != null
                ? model.getProgramDetails().stream()
                .map(ProgramDetailGetDto::fromModel)
                .toList()
                : List.of();

        return new PriceProgramGetDto(
                model.getId(),
                model.getProgramName(),
                model.getEffectiveDate() != null ? model.getEffectiveDate() : null,
                model.isActive(),
                details,
                model.getCreatedBy(),
                model.getCreatedOn(),
                model.getLastModifiedBy(),
                model.getLastModifiedOn()
        );
    }
}
