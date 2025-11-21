package com.evdealer.ev_dealer_management.warehouse.model.dto;

import com.evdealer.ev_dealer_management.warehouse.model.enumeration.WarehouseCarStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record WarehouseCarUpdateDto(

        @NotNull(message = "The carDetail id must not be null")
        Long carModelId,

        @PositiveOrZero(message = "The quantity must be greater than 0.")
        Integer quantity,

        WarehouseCarStatus warehouseCarStatus

){
}
