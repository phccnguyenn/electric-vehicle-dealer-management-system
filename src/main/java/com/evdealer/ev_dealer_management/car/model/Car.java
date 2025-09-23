package com.evdealer.ev_dealer_management.car.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id", nullable = false, unique = true)
    private long carId;
    @Column(name = "car_name",length = 50, nullable = false)
    private String carName;
    @Column(name = "car_model",length = 20, nullable = false)
    private String carModel;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "status", nullable = false)
    private Short status;

    @OneToMany(mappedBy = "car",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CarConfig> carConfigs = new ArrayList<>() ;



}
