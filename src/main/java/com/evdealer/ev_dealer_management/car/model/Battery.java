package com.evdealer.ev_dealer_management.car.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "batteries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Battery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "battery_id", nullable = false, unique = true)
    private String batteryId ;

    @Column(name = "battery_type",length = 100, nullable = false)
    private String batteryType ;

    @Column(name = "charging_duration", nullable = false)
    private String chargingDuration ;

    @Column(name = "duration_use", nullable = false)
    private String durationUse ;

    @Column(name = "weight", nullable = false,precision = 7, scale = 2)
    private String Weight ;

    @OneToMany(mappedBy = "battery",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CarConfig> carConfigs =new ArrayList<>() ;
}
