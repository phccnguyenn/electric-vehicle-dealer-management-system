package com.evdealer.ev_dealer_management.order.service;

import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.model.Payment;
import com.evdealer.ev_dealer_management.order.model.dto.*;
import com.evdealer.ev_dealer_management.order.repository.OrderRepository;
import com.evdealer.ev_dealer_management.order.repository.PaymentRepository;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.service.DealerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final DealerService dealerService;

    // Add a new payment to an order
    @Transactional
    public OrderDetailDto addPayment(Long orderId, PaymentDto dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = Payment.builder()
                .order(order)
                .amount(dto.amount())
                .type(dto.type())
                .paidAt(LocalDateTime.now())
                .build();

        order.addPayment(payment);
        paymentRepository.save(payment);
        orderRepository.save(order);

        return OrderDetailDto.fromModel(order);
    }

    // Get payments for an order
    public List<PaymentResponseDto> getPayments(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return order.getPayments().stream()
                .map(PaymentResponseDto::fromModel)
                .collect(Collectors.toList());
    }

    // Revenue / reporting
    public List<RevenueByStaffDto> getRevenueByStaff(Long staffId) {
        Long dealerId = dealerService.getCurrentUser().getId();
        return paymentRepository.getRevenueByStaff(staffId, dealerId);
    }

    public List<RevenueByDealerDto> getRevenueByDealer() {
        return paymentRepository.getRevenueByDealer();
    }

    public List<RevenueByCityDto> getRevenueByCity() {
        return paymentRepository.getRevenueByCity();
    }

    public List<CustomerDebtDto> getCustomerDebts() {
        Long dealerId = dealerService.getCurrentUser().getId();
        return paymentRepository.getCustomerDebts(dealerId);
    }
}
