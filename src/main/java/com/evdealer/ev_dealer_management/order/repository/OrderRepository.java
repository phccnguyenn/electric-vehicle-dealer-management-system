package com.evdealer.ev_dealer_management.order.repository;

import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.model.dto.SalesSpeedRequestDto;
import com.evdealer.ev_dealer_management.order.model.dto.SalesSpeedResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStaff_Id(Long staffId);
    //List<Order> findByCustomer_Id(Long customerId);
    //List<Order> findByManager_Id(Long managerId);
    @Query(value = """
        SELECT 
            s.parent_id AS dealerId,
            COUNT(o.id) * 1.0 / 
            (TIMESTAMPDIFF(MONTH, :startTime, :endTime) + 1) AS salesPerMonth
        FROM orders o
        JOIN users s ON o.staff_id = s.user_id
        WHERE o.status IN ('DELIVERED','COMPLETED')
          AND o.created_on BETWEEN :startTime AND :endTime
        GROUP BY s.parent_id
    """, nativeQuery = true)
    List<SalesSpeedResponseDto> getSalesSpeedByDealer(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
