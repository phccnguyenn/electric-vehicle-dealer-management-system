package com.evdealer.ev_dealer_management.order.model.dto;

import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;

import java.math.BigDecimal;

public record OrderUpdateDto(
        BigDecimal totalAmount,   // Can update total amount
        String quotationUrl,      // Update quotation
        String contractUrl,       // Update contract
        Long staffId,             // Optionally change staff
        OrderStatus status
) { }
