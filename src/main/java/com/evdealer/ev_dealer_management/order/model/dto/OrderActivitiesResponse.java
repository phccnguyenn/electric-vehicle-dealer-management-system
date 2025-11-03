package com.evdealer.ev_dealer_management.order.model.dto;

import java.util.List;

public record OrderActivitiesResponse(
        Long orderId,
        List<OrderActivityDto> activities
) {}
