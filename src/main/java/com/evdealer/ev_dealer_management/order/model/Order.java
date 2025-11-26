package com.evdealer.ev_dealer_management.order.model;


import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.car.model.CarModel;
import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
import com.evdealer.ev_dealer_management.order.model.enumeration.PaymentStatus;
import com.evdealer.ev_dealer_management.user.model.Customer;
import com.evdealer.ev_dealer_management.user.model.DealerInfo;
import com.evdealer.ev_dealer_management.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private CarDetail carDetail;

    @ManyToOne
    @JoinColumn(name = "dealer_id")
    private DealerInfo dealerInfo;

    // No order generation permission for Manager
    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private User staff;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "amount_paid")
    private BigDecimal amountPaid;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    // PDF/DOC links
    @Column(name = "contract_url")
    private String contractUrl;

    @Column(name = "quotation_url")
    private String quotationUrl;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderActivity> activities = new ArrayList<>();

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

        BigDecimal threshold = totalAmount.multiply(new BigDecimal("0.3"));

        if (totalPaid.compareTo(BigDecimal.ZERO) == 0) {
            paymentStatus = PaymentStatus.PENDING;
        }
        else if (totalPaid.compareTo(threshold) >= 0 && totalPaid.compareTo(totalAmount) < 0) {
            paymentStatus = PaymentStatus.DEPOSIT_PAID;
        }
        else if (totalPaid.compareTo(totalAmount) >= 0) {
            paymentStatus = PaymentStatus.FINISHED;
        }
    }
}
