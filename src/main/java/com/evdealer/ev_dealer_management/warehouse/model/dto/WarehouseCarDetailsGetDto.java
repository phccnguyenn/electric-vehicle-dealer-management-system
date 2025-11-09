package com.evdealer.ev_dealer_management.warehouse.model.dto;

import com.evdealer.ev_dealer_management.warehouse.model.WarehouseCar;
import com.evdealer.ev_dealer_management.warehouse.model.enumeration.InventoryStatus;

public record WarehouseCarDetailsGetDto(
        Long id,
        Long carId,
        Integer quantity
) {
    public static WarehouseCarDetailsGetDto fromModel(WarehouseCar warehouseCar) {
        return new WarehouseCarDetailsGetDto(
                warehouseCar.getId(),
                warehouseCar.getCarDetail().getId(),
                warehouseCar.getQuantity()
        );
    }
}
