package com.evdealer.ev_dealer_management.order.model.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record SalesSpeedRequestDto(
        LocalDateTime startTime,
        LocalDateTime endTime
) { }