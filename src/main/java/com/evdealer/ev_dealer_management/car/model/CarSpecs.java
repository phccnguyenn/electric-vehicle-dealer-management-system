package com.evdealer.ev_dealer_management.car.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "car_specs")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CarSpecs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specs_id", nullable = false, unique = true)
    private Long specsId ;

    @Column(name = "maximum_capacity", nullable = false)
    private Integer maximumCapacity ;

    @Column(name = "maximum_torque", nullable = false)
    private Integer maximumTorque ;

    @Column(name = "light_system", nullable = false)
    private String lightSystem ;

    @Column(name = "speaker_system", nullable = false)
    private String speakerSystem ;

    @Column(name = "ground_learance", nullable = false)
    private Integer groundClearance ;

    @Column(name = "seat", nullable = false)
    private Short seat ;

    @Column(name = "air_conditioner", nullable = false)
    private String airConditioner ;

    @Column(name = "distance_run", nullable = false)
    private Integer distanceRun ;

    @Column(name = "wheel_base", nullable = false)
    private String wheelBase ;

    @Column(name = "drive", nullable = false)
    private String drive ;

    @OneToOne (fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "config_id", nullable = false)
    private CarConfig config ;

}
