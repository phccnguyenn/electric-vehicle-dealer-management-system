package com.evdealer.ev_dealer_management.car.model;

import com.evdealer.ev_dealer_management.car.model.enumeration.CoolingType;
import com.evdealer.ev_dealer_management.car.model.enumeration.MotorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "motors")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Motor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "motor_type", nullable = false)
    private MotorType motorType;

    private String serialNumber;

    private Double powerKw;

    private Double torqueNm;

    private Integer maxRpm;

    @Enumerated(EnumType.STRING)
    @Column(name = "cooling_type")
    private CoolingType coolingType;

    private Double voltageRangeV;

    private Double weightKg;

}
