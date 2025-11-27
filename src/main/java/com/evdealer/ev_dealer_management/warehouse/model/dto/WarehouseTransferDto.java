package com.evdealer.ev_dealer_management.warehouse.model.dto;

import com.evdealer.ev_dealer_management.car.model.dto.details.CarDetailGetDto;
import com.evdealer.ev_dealer_management.warehouse.model.WarehouseTransfer;

import java.time.OffsetDateTime;

public record WarehouseTransferDto(
        Long id,
        CarDetailGetDto car,
        String fromLocation,
        String toLocation,
        String note,
        OffsetDateTime createdOn
) {

    public static WarehouseTransferDto fromModel(WarehouseTransfer wt) {
        return new WarehouseTransferDto(
                wt.getId(),
                CarDetailGetDto.fromModel(wt.getCar()),
                wt.getFromLocation(),
                wt.getToLocation(),
                wt.getNote(),
                wt.getCreatedOn()
        );
    }
}
