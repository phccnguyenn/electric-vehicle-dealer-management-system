package com.evdealer.ev_dealer_management.order.model.dto;

import com.evdealer.ev_dealer_management.car.model.dto.car.CarInfoGetDto;
import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
import com.evdealer.ev_dealer_management.order.model.enumeration.PaymentStatus;
import com.evdealer.ev_dealer_management.user.model.dto.account.UserDetailGetDto;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerDetailGetDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record OrderDetailDto(
        Long id,
        CarInfoGetDto car,
        UserDetailGetDto staff,
        CustomerDetailGetDto customer,
        BigDecimal totalAmount,
        BigDecimal amountPaid,
        String quotationUrl,
        String contractUrl,
        OrderStatus status,
        PaymentStatus paymentStatus,
        List<PaymentResponseDto> payments
) {
    public static OrderDetailDto fromModel(Order order) {
        return new OrderDetailDto(
                order.getId(),
                CarInfoGetDto.fromModel(order.getCar()),
                UserDetailGetDto.fromModel(order.getStaff()),
                CustomerDetailGetDto.fromModel(order.getCustomer()),
                order.getTotalAmount(),
                order.getAmountPaid(),
                order.getQuotationUrl(),
                order.getContractUrl(),
                order.getStatus(),
                order.getPaymentStatus(),
                order.getPayments().stream()
                        .map(PaymentResponseDto::fromModel)
                        .collect(Collectors.toList())
        );
    }
}
