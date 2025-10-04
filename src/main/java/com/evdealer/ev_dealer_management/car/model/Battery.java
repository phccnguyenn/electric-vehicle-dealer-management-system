package com.evdealer.ev_dealer_management.car.model;

import com.evdealer.ev_dealer_management.car.model.enumeration.Chemistry;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "battery")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Battery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "battery_id")
    private Long batteryId;


    @Enumerated(EnumType.STRING)
    private Chemistry chemistry;    // Enum: NCA, NCM, LFP, SolidState

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "charge_time", nullable = false)
    private Duration chargeTime;

    @Column(name = "usage_duration")
    private Duration usageDuration;

    @Column(name = "weight_kg")
    private float weightKg;

    @Column(name = "voltage_v")
    private float voltageV;

    @Column(name = "capacity_kwh")
    private float capacityKwh;

    @Column(name = "cycle_life")
    private int cycleLife;

    @OneToMany(mappedBy = "battery",fetch =  FetchType.LAZY,
                    cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Performance> performances = new ArrayList<>();
}
