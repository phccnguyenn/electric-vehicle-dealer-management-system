package com.evdealer.ev_dealer_management.car.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dimension", schema = "dbo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dimension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dimension_id")
    private Long dimensionId;

    @Column(name = "weight_lbs")
    private Float weightLbs;

    @Column(name = "ground_clearance_in")
    private Float groundClearanceIn;

    @Column(name = "width_folded_in")
    private Float widthFoldedIn;

    @Column(name = "width_extended_in")
    private Float widthExtendedIn;

    @Column(name = "height_in")
    private Float heightIn;

    @Column(name = "length_mm")
    private Float lengthMm;

    @Column(name = "length_in")
    private Float lengthIn;

    @Column(name = "wheels_size_cm")
    private Float wheelsSizeCm;

    // Dimension không cần biết Car.
}
