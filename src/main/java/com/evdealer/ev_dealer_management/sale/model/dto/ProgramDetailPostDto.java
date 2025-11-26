package com.evdealer.ev_dealer_management.sale.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProgramDetailPostDto (

        @NotNull(message = "Car model is required")
        Long carModelId,

        @NotNull(message = "Min price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Min price must be positive")
        BigDecimal minPrice,

        @NotNull(message = "Min price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Suggested price must be positive")
        BigDecimal suggestedPrice,

        @NotNull(message = "Max price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Max price must be positive")
        BigDecimal maxPrice,

        @NotNull(message = "Is special color is required")
        boolean isSpecialColor

){
}
