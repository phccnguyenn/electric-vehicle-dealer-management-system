package com.evdealer.ev_dealer_management.warehouse.model;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "dbo", name = "warehouse_car")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseCar extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_detail_id")
    private CarDetail carDetail;

    @Column(nullable = false)
    private Integer quantity;

}
