package com.evdealer.ev_dealer_management.stock.model.dto;


import java.util.List;

public record InventoryListDto(
        List<InventoryDetailsGetDto> inventoryInfoGetDtos,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
