package com.evdealer.ev_dealer_management.warehouse.model.dto;


import java.util.List;

public record WarehouseCarListDto(
        List<WarehouseCarDetailsGetDto> inventoryInfoGetDtos,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
