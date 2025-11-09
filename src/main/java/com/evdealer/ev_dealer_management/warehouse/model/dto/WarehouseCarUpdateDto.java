package com.evdealer.ev_dealer_management.warehouse.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record WarehouseCarUpdateDto(

        @NotNull(message = "The car id must not be null") Long carDetailId,
        @Min(value = 1) Integer quantity
){
}
