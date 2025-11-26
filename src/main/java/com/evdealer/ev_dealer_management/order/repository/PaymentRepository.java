package com.evdealer.ev_dealer_management.order.repository;

import com.evdealer.ev_dealer_management.order.model.Payment;
import com.evdealer.ev_dealer_management.order.model.dto.CustomerDebtDto;
import com.evdealer.ev_dealer_management.order.model.dto.RevenueByCityDto;
import com.evdealer.ev_dealer_management.order.model.dto.RevenueByDealerDto;
import com.evdealer.ev_dealer_management.order.model.dto.RevenueByStaffDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

//    @Query("""
//        SELECT
//            o.staff.id AS staffId,
//            o.staff.fullName AS staffName,
//            SUM(p.amount) AS revenue
//        FROM Payment p
//        JOIN p.order o
//        WHERE (:staffId IS NULL OR o.staff.id = :staffId)
//        AND o.staff.parent.id = :dealerId
//        GROUP BY o.staff.id, o.staff.fullName
//    """)
//    List<RevenueByStaffDto> getRevenueByStaff(@Param("staffId") Long staffId, @Param("dealerId") Long dealerId);

//    @Query("""
//        SELECT
//            o.customer.id AS customerId,
//            o.customer.fullName AS customerName,
//            (o.totalAmount - COALESCE(SUM(p.amount), 0)) AS debt
//        FROM Order o
//        LEFT JOIN o.payments p
//        WHERE o.customer.dealer.id = :dealerId
//        GROUP BY o.customer.id, o.customer.fullName, o.totalAmount
//        HAVING (o.totalAmount - COALESCE(SUM(p.amount), 0)) > 0
//    """)
//    List<CustomerDebtDto> getCustomerDebts(@Param("dealerId") Long dealerId);

//    @Query("""
//    SELECT o.dealerInfo.id AS dealerId,
//           o.dealerInfo.dealerName AS dealerName,
//           SUM(p.amount) AS revenue
//    FROM Payment p
//    JOIN p.order o
//    WHERE s.role IN ('DEALER_STAFF', 'DEALER_MANAGER')
//      AND o.dealerInfo IS NOT NULL
//      AND o.status IN ('DELIVERED', 'COMPLETED')
//    GROUP BY o.dealerInfo.id, o.dealerInfo.dealerName
//    """)
//    List<RevenueByDealerDto> getRevenueByDealer();
//
//    @Query("""
//    SELECT o.dealerInfo.location AS city,
//           SUM(p.amount) AS revenue
//    FROM Payment p
//    JOIN p.order o
//    JOIN o.staff s
//      AND o.dealerInfo IS NOT NULL
//      AND o.status IN ('DELIVERED','COMPLETED')
//    GROUP BY o.dealerInfo.location
//    """)
//    List<RevenueByCityDto> getRevenueByCity(String city);
//
//    List<Payment> findByOrderId(Long orderId);
}
