package com.evdealer.ev_dealer_management.stock.model.dto;

import com.evdealer.ev_dealer_management.stock.model.Inventory;
import com.evdealer.ev_dealer_management.stock.model.enumeration.InventoryStatus;

public record InventoryDetailsGetDto(
        Long id,
        Long carId,
        Integer quantity,
        Integer reservedQuantity,
        Integer availableQuantity,
        InventoryStatus status,
        String note
) {
    public static InventoryDetailsGetDto fromModel(Inventory inventory) {
        return new InventoryDetailsGetDto(
                inventory.getId(),
                inventory.getCar().getId(),
                inventory.getQuantity(),
                inventory.getReservedQuantity(),
                inventory.getAvailableQuantity(),
                inventory.getStatus(),
                inventory.getNotes()
        );
    }
}
