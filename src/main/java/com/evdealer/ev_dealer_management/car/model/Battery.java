package com.evdealer.ev_dealer_management.car.model;

import com.evdealer.ev_dealer_management.car.model.enumeration.Chemistry;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "battery")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Battery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Chemistry chemistry;    // Enum: NCA, NCM, LFP, SolidState

    private int age;

    private float chargeTime;

    private float usageDuration;

    private float weightKg;

    private float voltageV;

    private float capacityKwh;

    private int cycleLife;

}
