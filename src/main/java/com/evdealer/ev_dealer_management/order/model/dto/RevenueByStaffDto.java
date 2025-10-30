package com.evdealer.ev_dealer_management.order.model.dto;

import java.math.BigDecimal;

public interface RevenueByStaffDto {
    Long getStaffId();
    String getStaffName();
    BigDecimal getRevenue();
}
