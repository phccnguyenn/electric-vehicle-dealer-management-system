package com.evdealer.ev_dealer_management.car.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "performance", schema = "dbo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "performance_id, nullable = false")
    private Long performanceId;

    @Column(name = "range_miles")
    private Double rangeMiles;

    @Column(name = "acceleration_sec")
    private Double accelerationSec;

    @Column(name = "top_speed_mph")
    private Double topSpeedMph;

    @Column(name = "towing_lbs")
    private Double towingLbs;

    @ManyToOne()
    @JoinColumn(name = "battery_id")
    private Battery battery;

    @ManyToOne()
    @JoinColumn(name = "motor_id")
    private Motor motor;

}
