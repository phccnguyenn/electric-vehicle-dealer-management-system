package com.evdealer.ev_dealer_management.warehouse.model;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import com.evdealer.ev_dealer_management.warehouse.model.enumeration.WarehouseCarStatus;
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
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "car_detail_id")
    private CarDetail carDetail;

    private Integer quantity;

    @Column(name = "warehouse_car_status")
    @Enumerated(EnumType.STRING)
    private WarehouseCarStatus warehouseCarStatus;

}
