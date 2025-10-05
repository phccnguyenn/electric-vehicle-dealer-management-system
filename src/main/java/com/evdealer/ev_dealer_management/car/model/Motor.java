package com.evdealer.ev_dealer_management.car.model;

import com.evdealer.ev_dealer_management.car.model.enumeration.CoolingType;
import com.evdealer.ev_dealer_management.car.model.enumeration.MotorType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "motors")
@Setter
@Getter
@Builder
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
    private Float powerKw;

    @Column(name = "torque_nm")
    private Float torqueNm;

    @Column(name = "max_rpm")
    private Integer maxRpm;

    @Enumerated(EnumType.STRING)
    private CoolingType coolingType;

    @Column(name = "voltage_range_v")
    private Float voltageRangeV;

    @OneToMany(mappedBy = "motor",fetch = FetchType.LAZY)
    private List<Performance> performances = new ArrayList<>();
}
