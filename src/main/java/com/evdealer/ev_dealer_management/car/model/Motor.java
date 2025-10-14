package com.evdealer.ev_dealer_management.car.model;

import com.evdealer.ev_dealer_management.car.model.enumeration.CoolingType;
import com.evdealer.ev_dealer_management.car.model.enumeration.MotorType;
import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "dbo", name = "motor")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Motor extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long motorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "motor_type")
    private MotorType motorType;

    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    @Column(name = "power_kw")
    private Float powerKw;

    @Column(name = "torque_nm")
    private Float torqueNm;

    @Column(name = "max_rpm")
    private Integer maxRpm;

    @Enumerated(EnumType.STRING)
    @Column(name = "cooling_type")
    private CoolingType coolingType;

    @Column(name = "voltage_range_v")
    private Float voltageRangeV;

    @OneToMany(mappedBy = "motor")
    private List<Performance> performances = new ArrayList<>();
}
