package com.evdealer.ev_dealer_management.order.repository;

import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.model.dto.CustomerDebtDto;
import com.evdealer.ev_dealer_management.order.model.dto.RevenueByCityDto;
import com.evdealer.ev_dealer_management.order.model.dto.RevenueByDealerDto;
import com.evdealer.ev_dealer_management.order.model.dto.RevenueByStaffDto;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStaffUserId(Long staffUserId);
    List<Order> findByManagerId(Long managerId);
    List<Order> findByCustomerId(Long customerId);
}
