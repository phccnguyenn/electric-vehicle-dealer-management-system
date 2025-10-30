package com.evdealer.ev_dealer_management.order.service;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.repository.CarRepository;
import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.model.dto.OrderCreateDto;
import com.evdealer.ev_dealer_management.order.model.dto.OrderDetailDto;
import com.evdealer.ev_dealer_management.order.model.dto.OrderUpdateDto;
import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
import com.evdealer.ev_dealer_management.order.model.enumeration.PaymentStatus;
import com.evdealer.ev_dealer_management.order.repository.OrderRepository;
import com.evdealer.ev_dealer_management.user.model.Customer;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.repository.CustomerRepository;
import com.evdealer.ev_dealer_management.user.repository.UserRepository;
import com.itextpdf.text.DocumentException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    private final FileGenerator fileGenerator;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;


    public OrderDetailDto createOrder(OrderCreateDto dto) {
        Car car = carRepository.findById(dto.carId())
                .orElseThrow(() -> new RuntimeException("Car not found"));
        User staff = userRepository.findById(dto.staffId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Order order = Order.builder()
                .car(car)
                .staff(staff)
                .customer(customer)
                .totalAmount(dto.totalAmount())
                .amountPaid(BigDecimal.ZERO)
                .quotationUrl(dto.quotationUrl())
                .contractUrl(dto.contractUrl())
                .status(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        order = orderRepository.save(order); // Save entity, not DTO

        return OrderDetailDto.fromModel(order);
    }

    public OrderDetailDto getOrderDetail(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderDetailDto.fromModel(order);
    }

    public OrderDetailDto updateOrder(Long id, OrderUpdateDto dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (dto.totalAmount() != null) order.setTotalAmount(dto.totalAmount());
        if (dto.quotationUrl() != null) order.setQuotationUrl(dto.quotationUrl());
        if (dto.contractUrl() != null) order.setContractUrl(dto.contractUrl());
        if (dto.staffId() != null) {
            User staff = userRepository.findById(dto.staffId())
                    .orElseThrow(() -> new RuntimeException("Staff not found"));
            order.setStaff(staff);
        }

        return OrderDetailDto.fromModel(orderRepository.save(order));
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public List<Order> getOrders(Optional<Long> staffId, Optional<OrderStatus> status) {
        List<Order> orders;

        if (staffId.isPresent()) {
            orders = orderRepository.findByStaffUserId(staffId.get());
        } else {
            orders = orderRepository.findAll();
        }

        return orders.stream()
                .filter(o -> status.map(s -> o.getStatus() == s)
                        .orElse(o.getStatus() != OrderStatus.CANCELLED))
                .collect(Collectors.toList());
    }
}
