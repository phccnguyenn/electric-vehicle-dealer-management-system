package com.evdealer.ev_dealer_management.order.repository;

import com.evdealer.ev_dealer_management.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStaff_Id(Long staffId);
    List<Order> findByCustomer_Id(Long customerId);
    List<Order> findByManager_Id(Long managerId);
}
