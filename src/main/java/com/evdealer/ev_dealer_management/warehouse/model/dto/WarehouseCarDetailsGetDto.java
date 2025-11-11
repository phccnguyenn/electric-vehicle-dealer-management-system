package com.evdealer.ev_dealer_management.warehouse.model.dto;

import com.evdealer.ev_dealer_management.car.model.dto.details.CarDetailGetDto;
import com.evdealer.ev_dealer_management.warehouse.model.WarehouseCar;
import com.evdealer.ev_dealer_management.warehouse.model.enumeration.WarehouseCarStatus;

public record WarehouseCarDetailsGetDto(
        Long id,
        CarDetailGetDto carDetail,
        Integer quantity,
        WarehouseCarStatus warehouseCarStatus
) {
    public static WarehouseCarDetailsGetDto fromModel(WarehouseCar warehouseCar) {
        return new WarehouseCarDetailsGetDto(
                warehouseCar.getId(),
                CarDetailGetDto.fromModel(warehouseCar.getCarDetail()),
                warehouseCar.getQuantity(),
                warehouseCar.getWarehouseCarStatus()
        );
    }
}
