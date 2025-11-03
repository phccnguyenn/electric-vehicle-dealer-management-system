package com.evdealer.ev_dealer_management.order.model.dto;

import java.math.BigDecimal;

public record OrderCreateDto(
        Long carId,
        String customerPhone,
        BigDecimal totalAmount
) { }
