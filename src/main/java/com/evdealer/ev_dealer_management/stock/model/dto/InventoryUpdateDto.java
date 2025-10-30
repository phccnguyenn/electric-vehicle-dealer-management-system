package com.evdealer.ev_dealer_management.stock.model.dto;

import com.evdealer.ev_dealer_management.stock.model.enumeration.InventoryStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record InventoryUpdateDto (

        @Min(value = 1) Integer quantity,
        InventoryStatus status,
        @Size(max = 500) String notes

){
}
