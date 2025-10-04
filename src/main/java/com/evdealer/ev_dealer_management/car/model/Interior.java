package com.evdealer.ev_dealer_management.car.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interiors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Interior {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interior_id")
    private Long interiorId;

    @Column(name = "seating_material")
    private String seatingMaterial;

    @Column(name = "color")
    private String color;

    @Column(name = "head_up_display")
    private String headUpDisplay;

    @Column(name = "infotainment_touch_screen")
    private String infotainmentTouchScreen;

    @Column(name = "climate_control")
    private String climateControl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
}