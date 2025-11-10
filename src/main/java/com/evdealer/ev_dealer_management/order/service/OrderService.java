package com.evdealer.ev_dealer_management.order.service;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.car.repository.CarDetailRepository;
import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.model.dto.*;
import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
import com.evdealer.ev_dealer_management.order.model.enumeration.PaymentStatus;
import com.evdealer.ev_dealer_management.order.repository.OrderRepository;
import com.evdealer.ev_dealer_management.user.model.Customer;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.service.DealerService;
import com.itextpdf.text.DocumentException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final FileGenerator fileGenerator;
    private final DealerService dealerService;
    private final CarDetailRepository carDetailRepository;
    private final OrderActivityService orderActivityService;

    public OrderDetailDto createOrder(OrderCreateDto dto) {
        CarDetail carDetail = carDetailRepository.findById(dto.carId())
                .orElseThrow(() -> new RuntimeException("Car not found"));
        User staff = dealerService.getCurrentUser();
        Customer customer = dealerService.getCustomerByPhone(dto.customerPhone(), Customer.class);

        Order order = Order.builder()
                .carDetail(carDetail)
                .staff(staff)
                .customer(customer)
                .totalAmount(dto.totalAmount())
                .amountPaid(BigDecimal.ZERO)
                .status(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        order = orderRepository.save(order);
        orderActivityService.logActivity(order.getId(), OrderStatus.PENDING);
        // Save entity, not DTO
        try {
            fileGenerator.generateQuotation(order);
            fileGenerator.generateContract(order);
        } catch (IOException | DocumentException e) {
            throw new RuntimeException("Failed to generate files for the order", e);
        }

        return OrderDetailDto.fromModel(order);
    }

    public OrderDetailDto getOrderDetail(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderDetailDto.fromModel(order);
    }

    public List<OrderFileDto> getOrderFilesByCustomerPhone(String phone) {
        return orderRepository.findFileUrlsByCustomerPhone(phone);
    }
    public OrderDetailDto updateOrder(Long id, OrderUpdateDto dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (dto.status() != null && dto.status() != order.getStatus()) {
            orderActivityService.logActivity(order.getId(), dto.status());
        }

        if (dto.totalAmount() != null) order.setTotalAmount(dto.totalAmount());
        if (dto.quotationUrl() != null) order.setQuotationUrl(dto.quotationUrl());
        if (dto.contractUrl() != null) order.setContractUrl(dto.contractUrl());

        return OrderDetailDto.fromModel(orderRepository.save(order));
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        orderActivityService.logActivity(order.getId(), OrderStatus.CANCELLED);
    }

    public List<Order> getOrders(Optional<Long> staffId, Optional<OrderStatus> status) {
        List<Order> orders;

        if (staffId.isPresent()) {
            orders = orderRepository.findByStaff_Id(staffId.get());
        } else {
            orders = orderRepository.findAll();
        }

        return orders.stream()
                .filter(o -> status.map(s -> o.getStatus() == s)
                        .orElse(o.getStatus() != OrderStatus.CANCELLED))
                .collect(Collectors.toList());
    }
}
