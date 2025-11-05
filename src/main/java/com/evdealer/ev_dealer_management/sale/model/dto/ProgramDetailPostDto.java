package com.evdealer.ev_dealer_management.sale.model.dto;

import java.math.BigDecimal;

public record ProgramDetailPostDto (
        Long carId,
        BigDecimal minPrice,
        BigDecimal suggestedPrice,
        BigDecimal maxPrice
){
}
