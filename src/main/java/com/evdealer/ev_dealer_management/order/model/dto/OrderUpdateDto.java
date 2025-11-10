package com.evdealer.ev_dealer_management.order.model.dto;

import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record OrderUpdateDto(

        @DecimalMin(value = "0.0", inclusive = false, message = "Tổng số tiền phải lớn hơn 0")
        BigDecimal totalAmount,   // Có thể null nếu mình không muốn update

        @NotBlank(message = "URL của quotation không được để trống")
        @Pattern(
                regexp = "^(http|https)://.*$",
                message = "Quotation URL phải là URL hợp lệ"
        )
        String quotationUrl,      // Có thể null nếu không muốn update

        @NotBlank(message = "URL của contract không được để trống")
        @Pattern(
                regexp = "^(http|https)://.*$",
                message = "Contract URL phải là URL hợp lệ"
        )
        String contractUrl,       // Có thể null nếu không muốn update

        @NotNull(message = "Trạng thái đơn hàng không được để trống")
        OrderStatus status
) { }
