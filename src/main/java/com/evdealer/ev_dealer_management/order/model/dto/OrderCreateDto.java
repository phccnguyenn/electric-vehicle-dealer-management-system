package com.evdealer.ev_dealer_management.order.model.dto;

import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record OrderCreateDto(

        @NotBlank(message = "Tên khách hàng không được để trống")
        String customerName,

        @NotBlank(message = "Số điện thoại khách hàng không được để trống")
        @Pattern(
                regexp = "^(0)(\\d{9})$",
                message = "Số điện thoại không hợp lệ (phải có 10 chữ số, bắt đầu bằng 0)"
        )
        String customerPhone,

        OrderStatusCreate orderStatus,

        @NotNull(message = "Car ID không được để trống")
        Long carModelId,

        boolean isSpecialColor,

        @NotNull(message = "Tổng số tiền không được để trống")
        @DecimalMin(value = "0.0", inclusive = false, message = "Tổng số tiền phải lớn hơn 0")
        BigDecimal totalAmount,

        Long carDetailId

) { }
