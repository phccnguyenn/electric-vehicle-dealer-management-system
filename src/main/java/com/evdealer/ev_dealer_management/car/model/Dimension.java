package com.evdealer.ev_dealer_management.car.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dimensions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dimension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dimension_id")
    private Long dimensionId;

    @Column(name = "length_mm")
    private Double lengthMm;

    @Column(name = "weitht_lbs")
    private Double weightLbs;

    @Column(name = "ground_clearance_in")
    private Double groundClearanceIn;

    @Column(name = "width_folded_in")
    private Double widthFoldedIn;

    @Column(name = "width_extended_in")
    private Double widthExtendedIn;

    @Column(name = "height_in")
    private Double heightIn;

    @Column(name = "length_in")
    private Integer lengthIn;

    @Column(name = "wheels_size_cm")
    private Double wheelsSizeCm;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

}
