package com.evdealer.ev_dealer_management.order.repository;

import com.evdealer.ev_dealer_management.order.model.Payment;
import com.evdealer.ev_dealer_management.order.model.dto.CustomerDebtDto;
import com.evdealer.ev_dealer_management.order.model.dto.RevenueByCityDto;
import com.evdealer.ev_dealer_management.order.model.dto.RevenueByDealerDto;
import com.evdealer.ev_dealer_management.order.model.dto.RevenueByStaffDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("""
        SELECT 
            o.staff.id AS staffId,
            o.staff.fullName AS staffName,
            SUM(p.amount) AS revenue
        FROM Payment p
        JOIN p.order o
        WHERE o.staff.id = :staffId
        GROUP BY o.staff.id, o.staff.fullName
    """)
    RevenueByStaffDto getRevenueByStaff(@Param("staffId") Long staffId);

    @Query("""
        SELECT 
            o.customer.id AS customerId,
            o.customer.fullName AS customerName,
            (o.totalAmount - COALESCE(SUM(p.amount), 0)) AS debt
        FROM Order o
        LEFT JOIN o.payments p
        GROUP BY o.customer.id, o.customer.fullName, o.totalAmount
        HAVING (o.totalAmount - COALESCE(SUM(p.amount), 0)) > 0
    """)
    List<CustomerDebtDto> getCustomerDebts();

    @Query("""
        SELECT 
            o.staff.parent.id AS dealerId,
            o.staff.parent.fullName AS dealerName,
            SUM(p.amount) AS revenue
        FROM Payment p
        JOIN p.order o
        WHERE o.staff.parent.role = 'DEALER'
        GROUP BY o.staff.parent.id, o.staff.parent.fullName
    """)
    List<RevenueByDealerDto> getRevenueByDealer();

    @Query("SELECT s.parent.city, SUM(o.totalAmount) " +
            "FROM Order o JOIN o.staff s " +
            "WHERE o.status = 'COMPLETED' " +
            "GROUP BY s.parent.city")
    List<RevenueByCityDto> getRevenueByCity();

    List<Payment> findByOrderId(Long orderId);
}
