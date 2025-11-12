package com.evdealer.ev_dealer_management.warehouse.model.dto;

import com.evdealer.ev_dealer_management.warehouse.model.enumeration.WarehouseCarStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record WarehouseCarPostDto(

        Long warehouseId,

        @NotNull(message = "The carDetail ID is required.")
        Long carId,

        @NotNull(message = "The quantity is required.")
        @PositiveOrZero(message = "The quantity must be greater than 0.")
        Integer quantity,

        WarehouseCarStatus warehouseCarStatus
) {
        public WarehouseCarPostDto {
                if (warehouseId == null) {
                        warehouseId = 1L;
                }
        }
}
