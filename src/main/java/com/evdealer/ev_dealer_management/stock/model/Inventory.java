package com.evdealer.ev_dealer_management.stock.model;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import com.evdealer.ev_dealer_management.stock.model.enumeration.InventoryStatus;
import com.evdealer.ev_dealer_management.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "dbo", name = "inventory")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dealer_id", nullable = false)
    private User dealer;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "reserved_quantity", nullable = false)
    private Integer reservedQuantity;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "inventory_status", nullable = false)
    private InventoryStatus status;

    @Column(length = 500)
    private String notes;

}
