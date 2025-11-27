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

    @Query("""
    SELECT 
        s.id AS staffId,
        s.fullName AS staffName,
        SUM(o.totalAmount * dh.commissionRate) AS dealerRevenue
    FROM Order o
    JOIN o.staff s
    JOIN o.dealerInfo d
    JOIN d.dealerHierarchy dh
    WHERE d.id = :dealerId
      AND o.status = 'COMPLETED'
    GROUP BY s.id, s.fullName
""")
    List<RevenueByStaffDto> getRevenueByStaff(@Param("dealerId") Long dealerId);

    @Query("""
        SELECT
            o.customer.id AS customerId,
            o.customer.fullName AS customerName,
            SUM(o.totalAmount) - COALESCE(SUM(p.amount), 0) AS debt
        FROM Order o
        LEFT JOIN o.payments p
        WHERE o.customer.dealer.id = :dealerId
        GROUP BY o.customer.id, o.customer.fullName
        HAVING (SUM(o.totalAmount) - COALESCE(SUM(p.amount), 0)) > 0
    """)
    List<CustomerDebtDto> getCustomerDebts(@Param("dealerId") Long dealerId);

    // Cái này tính doanh thu thực nhận theo từng đợt khách thanh toán,
    @Query("""
    SELECT o.dealerInfo.id AS dealerId,
           o.dealerInfo.dealerName AS dealerName,
           SUM(p.amount * o.dealerInfo.dealerHierarchy.commissionRate) AS revenue
    FROM Payment p
    JOIN p.order o
    WHERE o.dealerInfo IS NOT NULL
      AND o.status = 'COMPLETED'
    GROUP BY o.dealerInfo.id, o.dealerInfo.dealerName
    """)
    List<RevenueByDealerDto> getRevenueByDealer();
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
