package com.evdealer.ev_dealer_management.order.model.dto.evm;

import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderUpdateForEVMDto (
        @NotNull(message = "Order ID cannot be null")
        @Min(value = 1, message = "Order ID must be greater than 0")
        Long orderId,

        @NotNull(message = "Car detail ID cannot be null")
        @Min(value = 1, message = "Car detail ID must be greater than 0")
        Long carDetailId,

        @NotNull(message = "Order status cannot be null")
        OrderStatus orderStatus
) {
}
