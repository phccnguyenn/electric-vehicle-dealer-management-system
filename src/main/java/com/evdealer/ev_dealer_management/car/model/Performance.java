package com.evdealer.ev_dealer_management.car.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.IntegerRange;

@Entity
@Table(schema = "dbo", name = "performance")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "battery_id")
    private Battery battery;

    @ManyToOne
    @JoinColumn(name = "motor_id")
    private Motor motor;

    @Column(name = "range_miles")
    private Float rangeMiles;

    @Column(name = "acceleration_sec")
    private Float accelerationSec;

    @Column(name = "top_speed_mph")
    private Float topSpeedMph;

    @Column(name = "towing_lbs")
    private Float towingLbs;
}
