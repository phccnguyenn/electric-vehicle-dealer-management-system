package com.evdealer.ev_dealer_management.order.model.dto;

import com.evdealer.ev_dealer_management.car.model.dto.details.CarInfoGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.model.CarModelGetByOrderDto;
import com.evdealer.ev_dealer_management.car.model.dto.model.CarModelGetDetailDto;
import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
import com.evdealer.ev_dealer_management.order.model.enumeration.PaymentStatus;
import com.evdealer.ev_dealer_management.user.model.dto.account.UserDetailGetDto;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerDetailGetDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record OrderDetailDto(
        Long id,
        CarModelGetByOrderDto carModelGetDetailDto,
        CarInfoGetDto carDetail,
        UserDetailGetDto staff,
        CustomerDetailGetDto customer,
        BigDecimal totalAmount,
        BigDecimal amountPaid,
        String quotationUrl,
        String contractUrl,
        OrderStatus status,
        PaymentStatus paymentStatus,
        Optional<List<PaymentResponseDto>> payments
) {
    public static OrderDetailDto fromModel(Order order) {

        Optional<List<PaymentResponseDto>> payments = Optional.of(
                order.getPayments() != null
                        ? order.getPayments().stream()
                        .map(PaymentResponseDto::fromModel)
                        .collect(Collectors.toList())
                        : List.of()
        );

        return new OrderDetailDto(
                order.getId(),
                order.getCarModel() != null ? CarModelGetByOrderDto.fromModel(order.getCarModel()) : null,
                order.getCarDetail() != null ? CarInfoGetDto.fromModel(order.getCarDetail()) : null,
                UserDetailGetDto.fromModel(order.getStaff()),
                CustomerDetailGetDto.fromModel(order.getCustomer()),
                order.getTotalAmount(),
                order.getAmountPaid(),
                order.getQuotationUrl(),
                order.getContractUrl(),
                order.getStatus(),
                order.getPaymentStatus(),
                payments
        );
    }
}
