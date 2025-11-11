package com.evdealer.ev_dealer_management.warehouse.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "dbo", name = "warehouse")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "warehouse_name")
    private String warehouseName;

    @OneToMany(mappedBy = "warehouse")
    List<WarehouseCar> warehouseCars = new ArrayList<>();

}
