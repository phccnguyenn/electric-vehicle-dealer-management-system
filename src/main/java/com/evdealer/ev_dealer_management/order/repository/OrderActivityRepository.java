package com.evdealer.ev_dealer_management.order.repository;

import com.evdealer.ev_dealer_management.order.model.OrderActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderActivityRepository extends JpaRepository<OrderActivity, Long> {
    List<OrderActivity> findByOrderIdOrderByChangedAtAsc(Long orderId);
}
