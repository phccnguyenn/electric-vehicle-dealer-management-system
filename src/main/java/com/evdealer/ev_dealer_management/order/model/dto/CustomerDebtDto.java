package com.evdealer.ev_dealer_management.order.model.dto;

import java.math.BigDecimal;

public interface CustomerDebtDto {
    Long getCustomerId();
    String getCustomerName();
    BigDecimal getDebt();
}
