package com.evdealer.ev_dealer_management.order.controller;

import com.evdealer.ev_dealer_management.order.model.dto.*;
import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
import com.evdealer.ev_dealer_management.order.service.OrderService;
import com.evdealer.ev_dealer_management.order.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order management APIs")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @Operation(summary = "Get order by ID")
    @GetMapping("/{id}")
    public OrderDetailDto getOrder(@PathVariable Long id) {
        return orderService.getOrderDetail(id);
    }

    @Operation(summary = "List orders")
    @GetMapping
    public List<OrderDetailDto> listOrders(
            @RequestParam Optional<Long> staffId,
            @RequestParam Optional<OrderStatus> status
    ) {
        return orderService.getOrders(staffId, status)
                .stream()
                .map(OrderDetailDto::fromModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Create a new order")
    @PostMapping
    public OrderDetailDto createOrder(@RequestBody OrderCreateDto dto) {
        return orderService.createOrder(dto);
    }

    @Operation(summary = "Add payment to an order")
    @PostMapping("/{id}/payments")
    public OrderDetailDto addPayment(@PathVariable Long id, @RequestBody PaymentDto paymentDto) {
        return paymentService.addPayment(id, paymentDto);
    }

    @Operation(summary = "Get payments of an order")
    @GetMapping("/{id}/payments")
    public List<PaymentResponseDto> getPayments(@PathVariable Long id) {
        return paymentService.getPayments(id);
    }

    @Operation(summary = "Soft-delete order by setting status to CANCELLED")
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
