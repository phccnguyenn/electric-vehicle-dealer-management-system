package com.evdealer.ev_dealer_management.car.model;

import com.evdealer.ev_dealer_management.car.model.enumeration.ChemistryType;
import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "dbo", name = "battery")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Battery extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChemistryType chemistryType;    // Enum: NCA, NCM, LFP, SolidState

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "charge_time_hour", nullable = false)
    private int chargeTime;

    @Column(name = "usage_duration_hour")
    private int usageDuration;

    @Column(name = "weight_kg")
    private float weightKg;

    @Column(name = "voltage_v")
    private float voltageV;

    @Column(name = "capacity_kwh")
    private float capacityKwh;

    @Column(name = "cycle_life")
    private int cycleLife;

    @OneToMany(mappedBy = "battery")
    private List<Performance> performances = new ArrayList<>();
}
