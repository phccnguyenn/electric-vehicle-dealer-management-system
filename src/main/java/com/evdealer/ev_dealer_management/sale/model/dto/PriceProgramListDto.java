package com.evdealer.ev_dealer_management.sale.model.dto;

import java.util.List;

public record PriceProgramListDto(
        List<PriceProgramGetDto> priceProgramGetDtoList,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
