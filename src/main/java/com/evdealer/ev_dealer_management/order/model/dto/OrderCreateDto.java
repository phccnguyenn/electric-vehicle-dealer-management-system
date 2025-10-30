package com.evdealer.ev_dealer_management.order.model.dto;

import java.math.BigDecimal;

public record OrderCreateDto(
        Long carId,
        Long staffId,
        Long customerId,
        BigDecimal totalAmount,
        String quotationUrl,     // Optional
        String contractUrl       // Optional
) { }
