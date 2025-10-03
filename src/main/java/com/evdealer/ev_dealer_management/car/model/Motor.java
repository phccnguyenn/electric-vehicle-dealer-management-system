package com.evdealer.ev_dealer_management.car.model;

import com.evdealer.ev_dealer_management.car.model.enumeration.CoolingType;
import com.evdealer.ev_dealer_management.car.model.enumeration.MotorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "motors")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Motor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "motor_id")
    private Long motorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "motor_type", nullable = false)
    private MotorType motorType;

    @Column(name = "manufacturer", unique = true,nullable = false)
    private String serialNumber;

    @Column(name = "power_kw")
    private Double powerKw;

    @Column(name = "torque_nm")
    private Double torqueNm;

    @Column(name = "max_rpm")
    private Integer maxRpm;

    @Enumerated(EnumType.STRING)
    @Column(name = "cooling_type")
    private CoolingType coolingType;

    @Column(name = "voltage_range_v")
    private Double voltageRangeV;

    @Column(name = "weight_kg")
    private Double weightKg;

    @OneToMany(mappedBy = "motor",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Performance> performances = new ArrayList<>();
}
