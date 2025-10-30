package com.evdealer.ev_dealer_management.order.model.dto;

import com.evdealer.ev_dealer_management.order.model.enumeration.PaymentType;

import java.math.BigDecimal;

public record PaymentDto(
        BigDecimal amount,
        PaymentType type
) { }
