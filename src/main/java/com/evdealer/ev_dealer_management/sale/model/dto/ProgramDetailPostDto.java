package com.evdealer.ev_dealer_management.sale.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProgramDetailPostDto (

        @NotNull(message = "Car model is required")
        Long carModelId,

        @NotNull(message = "Listed price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Listed price must be positive")
        BigDecimal listedPrice,

        @NotNull(message = "Is special color is required")
        boolean isSpecialColor

){
}
