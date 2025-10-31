package com.evdealer.ev_dealer_management.order.model;


import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
import com.evdealer.ev_dealer_management.order.model.enumeration.PaymentStatus;
import com.evdealer.ev_dealer_management.order.model.enumeration.PaymentType;
import com.evdealer.ev_dealer_management.user.model.Customer;
import com.evdealer.ev_dealer_management.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders", schema = "dbo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private User staff;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private BigDecimal totalAmount;
    private BigDecimal amountPaid;

    // PDF/DOC links
    private String quotationUrl;
    private String contractUrl;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    public void addPayment(Payment payment) {
        payment.setOrder(this);
        payments.add(payment);
        updatePaymentStatus();
    }

    // Cập nhật trạng thái thanh toán
    public void updatePaymentStatus() {
        BigDecimal totalPaid = payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add); // gán kết quả mới

        this.amountPaid = totalPaid;

        if(totalPaid.compareTo(BigDecimal.ZERO) == 0) {
            paymentStatus = PaymentStatus.PENDING;
        } else if(totalPaid.compareTo(totalAmount.multiply(new BigDecimal("0.3"))) <= 0) {
            paymentStatus = PaymentStatus.DEPOSIT_PAID;
        } else if(totalPaid.compareTo(totalAmount) < 0) {
            paymentStatus = PaymentStatus.PARTIAL;
        } else {
            paymentStatus = PaymentStatus.FINISHED;
        }
    }
}
