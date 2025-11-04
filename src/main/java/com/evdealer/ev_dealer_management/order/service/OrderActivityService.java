package com.evdealer.ev_dealer_management.order.service;

import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.model.OrderActivity;
import com.evdealer.ev_dealer_management.order.model.dto.SalesSpeedResponseDto;
import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
import com.evdealer.ev_dealer_management.order.repository.OrderActivityRepository;
import com.evdealer.ev_dealer_management.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderActivityService {

    private final OrderActivityRepository orderActivityRepository;
    private final OrderRepository orderRepository;

    /**
     * Ghi nhận hoạt động mới (khi trạng thái đơn hàng thay đổi)
     */
    @Transactional
    public void logActivity(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderActivity activity = OrderActivity.builder()
                .order(order)
                .status(newStatus)
                .changedAt(LocalDateTime.now())
                .build();

        orderActivityRepository.save(activity);

        order.setStatus(newStatus);
        orderRepository.save(order);
    }

    /**
     * Lấy toàn bộ lịch sử trạng thái của 1 đơn hàng
     */
    public List<OrderActivity> getActivities(Long orderId) {
        return orderActivityRepository.findByOrderIdOrderByChangedAtAsc(orderId);
    }

    public List<SalesSpeedResponseDto> getSalesSpeedByDealer(LocalDateTime startTime, LocalDateTime endTime) {
        return orderActivityRepository.getSalesSpeedByDealer(startTime, endTime);
    }
}
