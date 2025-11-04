package com.evdealer.ev_dealer_management.order.repository;

import com.evdealer.ev_dealer_management.order.model.OrderActivity;
import com.evdealer.ev_dealer_management.order.model.dto.SalesSpeedResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderActivityRepository extends JpaRepository<OrderActivity, Long> {
    List<OrderActivity> findByOrderIdOrderByChangedAtAsc(Long orderId);

    @Query(value = """
    SELECT 
        s.parent_id AS dealerId,
        COUNT(DISTINCT oa.order_id) * 1.0 / (DATEDIFF(MONTH, :startTime, :endTime) + 1) AS salesPerMonth
    FROM dbo.order_activities oa
    JOIN dbo.orders o ON oa.order_id = o.id
    JOIN dbo.users s ON o.staff_id = s.user_id
    WHERE oa.status IN ('DELIVERED', 'COMPLETED')
      AND oa.changed_at BETWEEN :startTime AND :endTime
    GROUP BY s.parent_id
""", nativeQuery = true)
    List<SalesSpeedResponseDto> getSalesSpeedByDealer(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

}
