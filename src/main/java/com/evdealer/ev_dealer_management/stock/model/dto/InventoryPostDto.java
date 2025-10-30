package com.evdealer.ev_dealer_management.stock.model.dto;

import com.evdealer.ev_dealer_management.stock.model.enumeration.InventoryStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record InventoryPostDto(

        @NotNull(message = "The car ID is required.")
        Long carId,

        @NotNull(message = "The quantity is required.")
        @Positive(message = "The quantity must be greater than 0.")
        Integer quantity,

        @PositiveOrZero Integer reservedQuantity,

        InventoryStatus inventoryStatus,

        @Size(max = 500, message = "The note max length is 500")
        String note
) {
}
