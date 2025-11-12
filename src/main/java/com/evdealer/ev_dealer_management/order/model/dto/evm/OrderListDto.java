package com.evdealer.ev_dealer_management.order.model.dto.evm;

import com.evdealer.ev_dealer_management.order.model.dto.OrderDetailDto;

import java.util.List;

public record OrderListDto (
        List<OrderDetailDto> orderDetailDtos,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
