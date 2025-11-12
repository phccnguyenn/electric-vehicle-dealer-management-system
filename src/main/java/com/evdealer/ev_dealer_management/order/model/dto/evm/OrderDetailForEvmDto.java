package com.evdealer.ev_dealer_management.order.model.dto.evm;

import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.model.dto.OrderDetailDto;

public record OrderDetailForEvmDto (
        OrderDetailDto orderDetailDto
        // CarModelGetDetailDto carModelGetDetailDto
) {
    public static OrderDetailForEvmDto fromModel(Order order) {
        return new OrderDetailForEvmDto(
                OrderDetailDto.fromModel(order)
        );
    }
}
