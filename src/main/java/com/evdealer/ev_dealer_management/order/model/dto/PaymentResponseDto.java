package com.evdealer.ev_dealer_management.order.model.dto;

import com.evdealer.ev_dealer_management.order.model.Payment;
import com.evdealer.ev_dealer_management.order.model.enumeration.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDto(
        Long id,
        BigDecimal amount,
        PaymentType type,
        LocalDateTime paidAt
) {
    public static PaymentResponseDto fromModel(Payment payment) {
        return new PaymentResponseDto(
                payment.getId(),
                payment.getAmount(),
                payment.getType(),
                payment.getPaidAt()
        );
    }
}
