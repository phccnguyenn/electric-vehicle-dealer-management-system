package com.evdealer.ev_dealer_management.order.model.dto;

import java.math.BigDecimal;

public interface RevenueByDealerDto {
    Long getDealerId();
    String getDealerName();
    BigDecimal getRevenue();
}
