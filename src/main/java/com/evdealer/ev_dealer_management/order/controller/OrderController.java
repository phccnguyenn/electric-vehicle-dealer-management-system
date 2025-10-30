package com.evdealer.ev_dealer_management.order.controller;

import com.evdealer.ev_dealer_management.order.model.dto.*;
import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
import com.evdealer.ev_dealer_management.order.service.OrderService;
import com.evdealer.ev_dealer_management.order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final PaymentService paymentService;

    // --- Orders ---
    @GetMapping
    public List<OrderDetailDto> getOrders(
            @RequestParam Optional<Long> staffId,
            @RequestParam Optional<OrderStatus> status
    ) {
        return orderService.getOrders(staffId, status)
                .stream()
                .map(OrderDetailDto::fromModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderDetailDto getOrder(@PathVariable Long id) {
        return orderService.getOrderDetail(id);
    }

    @PostMapping
    public OrderDetailDto createOrder(@RequestBody OrderCreateDto dto) {
        return orderService.createOrder(dto);
    }

    @PutMapping("/{id}")
    public OrderDetailDto updateOrder(@PathVariable Long id,
                                      @RequestBody OrderUpdateDto dto) {
        return orderService.updateOrder(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    // --- Payments ---
    @PostMapping("/{id}/payments")
    public OrderDetailDto addPayment(@PathVariable Long id, @RequestBody PaymentDto paymentDto) {
        return paymentService.addPayment(id, paymentDto);
    }

    @GetMapping("/{id}/payments")
    public List<PaymentResponseDto> getPayments(@PathVariable Long id) {
        return paymentService.getPayments(id);
    }

    // --- Revenue / Debt ---
    @GetMapping("/revenue/staff/{staffId}")
    public RevenueByStaffDto getRevenueByStaff(@PathVariable Long staffId) {
        return paymentService.getRevenueByStaff(staffId);
    }

    @GetMapping("/revenue/dealer")
    public List<RevenueByDealerDto> getRevenueByDealer() {
        return paymentService.getRevenueByDealer();
    }

    @GetMapping("/revenue/city")
    public List<RevenueByCityDto> getRevenueByCity() {
        return paymentService.getRevenueByCity();
    }

    @GetMapping("/debts/customers")
    public List<CustomerDebtDto> getCustomerDebts() {
        return paymentService.getCustomerDebts();
    }
}
