package com.evdealer.ev_dealer_management.order.repository;

import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.model.dto.OrderFileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStaff_Id(Long staffId);
    //List<Order> findByCustomer_Id(Long customerId);
    //List<Order> findByManager_Id(Long managerId);

    @Query("""
        SELECT new com.evdealer.ev_dealer_management.order.model.dto.OrderFileDto(o.quotationUrl, o.contractUrl)
        FROM Order o
        WHERE o.customer.phone = :phone
    """)
    List<OrderFileDto> findFileUrlsByCustomerPhone(@Param("phone") String phone);
}
