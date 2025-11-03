package com.evdealer.ev_dealer_management.order.model.dto;

import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;

import java.time.LocalDateTime;

public record OrderActivityDto(
        Long id,
        OrderStatus status,
        LocalDateTime changedAt
) {}
